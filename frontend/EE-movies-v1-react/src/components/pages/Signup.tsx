import React, { useState } from "react";
import axios from "axios";
import "./signup.css";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage(null);
    setSuccessMessage(null);

    try {
      const response = await axios.post(
        "http://localhost:8080/users/create/newUser",
        {
          username,
          password,
        }
      );

      if (response.status === 201) {
        setSuccessMessage("User created successfully! You can now log in.");
        setUsername("");
        setPassword("");
      } else {
        setErrorMessage("Unexpected response from the server.");
      }
    } catch (error: any) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data.message || "Registration failed.");
      } else {
        setErrorMessage("Network error or server not responding.");
      }
    }
  };

  return (
    <div className="signup-container">
      <h1 className="signup-header">Sign Up</h1>
      {errorMessage && (
        <div className="signup-message signup-error">{errorMessage}</div>
      )}
      {successMessage && (
        <div className="signup-message signup-success">{successMessage}</div>
      )}
      <form onSubmit={handleSubmit}>
        <label className="signup-label" htmlFor="username">
          Email:
        </label>
        <input
          id="username"
          type="email"
          className="signup-input"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <label className="signup-label" htmlFor="password">
          Password:
        </label>
        <input
          id="password"
          type="password"
          className="signup-input"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" className="signup-button">
          Create Account
        </button>
      </form>
    </div>
  );
}
