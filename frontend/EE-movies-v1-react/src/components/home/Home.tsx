import Hero from "../hero/Hero";

interface Movie {
  id: string;
  backdrop_path: string;
  poster_path: string;
  title: string;
  trailerLink?: string;
}

type HomeProps = {
  movies: Movie[];
};

const Home: React.FC<HomeProps> = ({ movies }) => {
  return <Hero movies={movies} />;
};

export default Home;
