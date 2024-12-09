import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom"; // Remove BrowserRouter import here
import Header from "./components/header/Header";
import Home from "./components/home/Home";
import Login from "./components/pages/Login";
import SignUp from "./components/pages/Signup";
import axiosConfig from "./api/axiosConfig";

function App() {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const response = await axiosConfig.get("/movies");
        setMovies(response.data);
      } catch (error) {
        console.error("Failed to fetch movies:", error);
      }
    };

    fetchMovies();
  }, []);

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home movies={movies} />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="*" element={<h1>404 - Page Not Found</h1>} />
      </Routes>
    </>
  );
}

export default App;
