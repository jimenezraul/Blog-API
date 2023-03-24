import { useRoutes } from "react-router-dom";
import { protectedRoutes } from "./protected";
import { publicRoutes } from "./public";


export default function AppRoutes() {
  const isLogged = localStorage.getItem("isLogged");

  const routes = isLogged ? protectedRoutes : publicRoutes;
  const routing = useRoutes([...routes]);
  return <>{routing}</>;
}
