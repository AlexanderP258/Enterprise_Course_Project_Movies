import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faVideoSlash } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink, useNavigate } from "react-router-dom";
import axiosConfig from "../../api/axiosConfig";
const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const handleDeleteAccount = async () => {
    try {
      const response = await axiosConfig.delete("/users/delete");
      if (response.status === 200) {
        alert("Your account has been deleted.");
        handleLogout();
      } else {
        alert("Failed to delete account.");
      }
    } catch (error) {
      console.error("Error deleting account:", error);
      alert("Error deleting account.");
    }
  };

  const isLoggedIn = localStorage.getItem("token");

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container fluid>
        <Navbar.Brand as={NavLink} to="/" style={{ color: "gold" }}>
          <FontAwesomeIcon icon={faVideoSlash} />
          EA Movies
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: "100px" }}
            navbarScroll
          ></Nav>
          <div>
            {isLoggedIn ? (
              <>
                <Button
                  variant="outline-warning"
                  className="me-2"
                  onClick={handleLogout}
                >
                  Logout
                </Button>
                <Button variant="outline-danger" onClick={handleDeleteAccount}>
                  Delete Account
                </Button>
              </>
            ) : (
              <>
                <Button
                  variant="outline-info"
                  className="me-2"
                  as={NavLink}
                  to="/login"
                >
                  Login
                </Button>
                <Button variant="outline-warning" as={NavLink} to="/signup">
                  Sign Up
                </Button>
              </>
            )}
          </div>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
