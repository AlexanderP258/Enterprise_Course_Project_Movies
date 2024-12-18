import React, { useState } from "react";
import axios from "axios";
import "./login.css";
import { useNavigate } from "react-router-dom";

interface LoginProps {
  onLogin: (token: string) => void;
}

const Login: React.FC<LoginProps> = ({ onLogin }) => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage(null);

    try {
      const response = await axios.post("http://localhost:8080/users/login", {
        username,
        password,
      });

      if (response.status === 200) {
        const token = response.data.token;
        localStorage.setItem("token", token);
        onLogin(token);
        navigate("/");
      } else {
        setErrorMessage("Unexpected response from the server.");
      }
    } catch (error: any) {
      if (error.response && error.response.data) {
        setErrorMessage(
          error.response.data.message || "Invalid login credentials."
        );
      } else {
        setErrorMessage("Network error or server not responding.");
      }
    }
  };

  return (
    <div className="login-container">
      <h1 className="login-header">Login</h1>
      {errorMessage && <div className="login-error">{errorMessage}</div>}
      <form onSubmit={handleLogin}>
        <label htmlFor="username" className="login-label">
          Email:
        </label>
        <input
          type="text"
          id="username"
          className="login-input"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <label htmlFor="password" className="login-label">
          Password:
        </label>
        <input
          type="password"
          id="password"
          className="login-input"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" className="login-button">
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
