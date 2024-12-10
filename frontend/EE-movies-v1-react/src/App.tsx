import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import Header from "./components/header/Header";
import Home from "./components/home/Home";
import Login from "./components/pages/Login";
import SignUp from "./components/pages/Signup";
import axiosConfig from "./api/axiosConfig";

function App() {
  const [movies, setMovies] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token") || "");

  useEffect(() => {
    const fetchMovies = async () => {
      if (token) {
        try {
          const response = await axiosConfig.get("/api/v1/movies");
          setMovies(response.data);
        } catch (error) {
          console.error("Failed to fetch movies:", error);
        }
      }
    };

    fetchMovies();
  }, [token]);

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home movies={movies} />} />
        <Route path="/login" element={<Login onLogin={setToken} />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="*" element={<h1>404 - Page Not Found</h1>} />
      </Routes>
    </>
  );
}

export default App;
