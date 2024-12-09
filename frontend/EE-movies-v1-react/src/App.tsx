import { Routes, Route } from "react-router-dom";
import Header from "./components/header/Header";

const Login = () => <h1>Login Page</h1>;
const SignUp = () => <h1>Sign Up Page</h1>;

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<h1>Home Page</h1>} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="*" element={<h1>404 - Page Not Found</h1>} />
      </Routes>
    </div>
  );
}

export default App;
