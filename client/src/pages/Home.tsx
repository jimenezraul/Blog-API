import Blog from "../components/Blog";
import Header from "../components/Header";

const Home = () => {
  return (
    <div className="flex flex-1 flex-col">
          <Header />
          <Blog />
    </div>
  );
};

export default Home;
