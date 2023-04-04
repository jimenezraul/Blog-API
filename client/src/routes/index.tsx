import { useRoutes } from "react-router-dom";
import { protectedRoutes } from "./protected";
import { publicRoutes } from "./public";
import Auth from "../auth";


export default function AppRoutes() {
  const isLogged = Auth.isAuthenticated();

  const routes = isLogged ? protectedRoutes : publicRoutes;
  const routing = useRoutes([...routes]);
  return <>{routing}</>;
}
