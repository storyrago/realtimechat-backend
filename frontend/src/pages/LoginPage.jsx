import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api, extractErrorMessage } from "../lib/api";
import { setAccessToken } from "../lib/storage";

const demoCredentials = {
  email: "",
  password: ""
};

export default function LoginPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState(demoCredentials);
  const [error, setError] = useState("");
  const [pending, setPending] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setPending(true);
    setError("");

    try {
      const { data } = await api.post("/auth/login", form);
      setAccessToken(data.accessToken);
      navigate("/chatrooms", { replace: true });
    } catch (submitError) {
      setError(extractErrorMessage(submitError, "로그인에 실패했습니다."));
    } finally {
      setPending(false);
    }
  };

  return (
    <main className="auth-page">
      <section className="auth-panel">
        <div className="auth-copy">
          <p className="eyebrow">Realtime Messaging</p>
          <h1>채팅 서비스 로그인</h1>
          <p className="muted">
            로그인 후 채팅방에 입장하고 WebSocket 연결 상태를 바로 확인할 수 있습니다.
          </p>
        </div>

        <form className="auth-form" onSubmit={handleSubmit}>
          <label className="field">
            <span>이메일</span>
            <input
              name="email"
              type="email"
              placeholder="test@example.com"
              value={form.email}
              onChange={handleChange}
              autoComplete="username"
              required
            />
          </label>

          <label className="field">
            <span>비밀번호</span>
            <input
              name="password"
              type="password"
              placeholder="비밀번호"
              value={form.password}
              onChange={handleChange}
              autoComplete="current-password"
              required
            />
          </label>

          {error ? <p className="error-text">{error}</p> : null}

          <button className="primary-button" type="submit" disabled={pending}>
            {pending ? "로그인 중..." : "로그인"}
          </button>
        </form>
      </section>
    </main>
  );
}
