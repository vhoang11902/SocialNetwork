// IMPORT
import Login from "../pages/auth/login";
import Home from "../pages/client/home";
import Profile from "../pages/client/profile";
import { HomeLayouts, ProfileLayouts } from "../layouts";
import Register from "../pages/auth/register";


// PRIVATE

const privateRoutes = [
  {path: "/",component: Home,layout: HomeLayouts,},
  { path: "/home",component: Home,layout: HomeLayouts,},
  { path: "/profile/:id", component: Profile, layout: ProfileLayouts },
  ];
  export { privateRoutes };

// PUBLIC

const publicRoutes = [

    { path: "/login", component: Login},
    { path: "/register", component: Register},
  ];
  export { publicRoutes };
