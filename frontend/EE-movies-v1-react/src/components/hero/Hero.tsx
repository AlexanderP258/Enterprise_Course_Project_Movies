import "./Hero.css";
import Carousel from "react-material-ui-carousel";
import { Paper } from "@mui/material";

interface Movie {
  id: string;
  backdrop_path: string;
  poster_path: string;
  title: string;
}

type HeroProps = {
  movies: Movie[];
};

const Hero: React.FC<HeroProps> = ({ movies }) => {
  return (
    <div className="movie-carousel-container">
      <Carousel>
        {movies?.map((movie, index) => (
          <Paper key={movie.id || index} className="movie-card-container">
            <div
              className="movie-card"
              style={
                {
                  "--img": `url(${movie.backdrop_path})`,
                } as React.CSSProperties
              }
            >
              <div className="movie-detail">
                <div className="movie-poster">
                  <img src={movie.poster_path} alt={movie.title} />
                </div>
                <div className="movie-title">
                  <h4>{movie.title}</h4>
                </div>
              </div>
            </div>
          </Paper>
        ))}
      </Carousel>
    </div>
  );
};

export default Hero;
