package com.example.springboot_realtimechat.global.exception;

public enum ErrorCode {
    MEMBER_NOT_FOUND(404, "멤버 없음"),
    DUPLICATE_EMAIL(409, "이미 존재하는 이메일"),

    CHAT_ROOM_NOT_FOUND(404, "채팅방 없음"),

    ALREADY_JOINED_ROOM(409, "이미 참여한 채팅방"),
    NOT_JOINED_ROOM(403, "참여하지 않은 채팅방"),

    MESSAGE_NOT_FOUND(404, "메시지 없음"),

    INVALID_INPUT_VALUE(400, "잘못된 입력값"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류");
    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
}
