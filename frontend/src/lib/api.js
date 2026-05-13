import axios from "axios";
import { clearAccessToken, getAccessToken } from "./storage";

export const api = axios.create({
  baseURL: "/api"
});

api.interceptors.request.use((config) => {
  const token = getAccessToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      clearAccessToken();
    }

    return Promise.reject(error);
  }
);

export function extractErrorMessage(error, fallback) {
  return error.response?.data?.message || error.message || fallback;
}
