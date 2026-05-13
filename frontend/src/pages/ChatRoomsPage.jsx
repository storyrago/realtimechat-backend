import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { api, extractErrorMessage } from "../lib/api";
import { clearAccessToken } from "../lib/storage";

export default function ChatRoomsPage() {
  const navigate = useNavigate();
  const [chatRooms, setChatRooms] = useState([]);
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);

  const loadChatRooms = async () => {
    setLoading(true);
    setError("");

    try {
      const { data } = await api.get("/chatrooms");
      setChatRooms(data);
    } catch (loadError) {
      setError(extractErrorMessage(loadError, "채팅방 목록을 불러오지 못했습니다."));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadChatRooms();
  }, []);

  const handleCreateRoom = async (event) => {
    event.preventDefault();

    if (!name.trim()) {
      return;
    }

    setCreating(true);
    setError("");

    try {
      const { data } = await api.post("/chatrooms", { name: name.trim() });
      setName("");
      navigate(`/chatrooms/${data.id}`);
    } catch (createError) {
      setError(extractErrorMessage(createError, "채팅방 생성에 실패했습니다."));
    } finally {
      setCreating(false);
    }
  };

  const handleLogout = () => {
    clearAccessToken();
    navigate("/login", { replace: true });
  };

  return (
    <main className="app-page">
      <header className="topbar">
        <div>
          <p className="eyebrow">Lobby</p>
          <h1>채팅방</h1>
        </div>
        <button className="secondary-button" type="button" onClick={handleLogout}>
          로그아웃
        </button>
      </header>

      <section className="room-layout">
        <form className="room-create-panel" onSubmit={handleCreateRoom}>
          <div>
            <h2>새 채팅방</h2>
            <p className="muted">방을 만들고 바로 입장할 수 있습니다.</p>
          </div>

          <label className="field">
            <span>채팅방 이름</span>
            <input
              value={name}
              onChange={(event) => setName(event.target.value)}
              placeholder="예: 백엔드 스터디"
              required
            />
          </label>

          <button className="primary-button" type="submit" disabled={creating}>
            {creating ? "생성 중..." : "채팅방 생성"}
          </button>

          {error ? <p className="error-text">{error}</p> : null}
        </form>

        <section className="room-list-panel">
          <div className="section-heading">
            <h2>전체 채팅방</h2>
            <button className="secondary-button" type="button" onClick={loadChatRooms}>
              새로고침
            </button>
          </div>

          {loading ? <p className="muted">불러오는 중...</p> : null}

          {!loading && chatRooms.length === 0 ? (
            <p className="muted">생성된 채팅방이 없습니다.</p>
          ) : null}

          <ul className="room-list">
            {chatRooms.map((chatRoom) => (
              <li key={chatRoom.id} className="room-row">
                <div>
                  <strong>{chatRoom.name}</strong>
                  <p className="muted">Room #{chatRoom.id}</p>
                </div>
                <Link className="primary-link" to={`/chatrooms/${chatRoom.id}`}>
                  입장
                </Link>
              </li>
            ))}
          </ul>
        </section>
      </section>
    </main>
  );
}
