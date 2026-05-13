import { Client } from "@stomp/stompjs";

function createBrokerUrl() {
  const protocol = window.location.protocol === "https:" ? "wss" : "ws";
  return `${protocol}://${window.location.host}/ws`;
}

export function createChatClient({ token, roomId, onMessage, onStatusChange, onError }) {
  const client = new Client({
    brokerURL: createBrokerUrl(),
    reconnectDelay: 5000,
    connectHeaders: {
      Authorization: `Bearer ${token}`
    },
    onConnect: () => {
      onStatusChange("connected");

      client.subscribe(`/sub/chatrooms/${roomId}`, (frame) => {
        try {
          const body = JSON.parse(frame.body);
          onMessage(body);
        } catch (error) {
          onError("메시지 파싱에 실패했습니다.");
        }
      });
    },
    onStompError: (frame) => {
      onStatusChange("error");
      onError(frame.headers.message || "STOMP 오류가 발생했습니다.");
    },
    onWebSocketClose: () => {
      onStatusChange("disconnected");
    },
    onWebSocketError: () => {
      onStatusChange("error");
      onError("WebSocket 연결에 실패했습니다.");
    }
  });

  return {
    activate() {
      onStatusChange("connecting");
      client.activate();
    },
    publishMessage(content) {
      client.publish({
        destination: `/pub/chatrooms/${roomId}/messages`,
        body: JSON.stringify({ content })
      });
    },
    deactivate() {
      return client.deactivate();
    }
  };
}
