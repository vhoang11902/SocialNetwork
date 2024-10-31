import Header from "./Header/header";
import SideBarMenu from "../../components/sideBarMenu";
import MyContext from "../../config/MyContext";
import Contact from "../../components/contact";
import { NotisProvider } from "../../config/noti";
import { useState } from "react";
function HomeLayouts({ children }) {
  return (
    <div className="font-body bg-gray-100">
    <NotisProvider>
        <Header />

        <div className="grid grid-cols-12">
          <div className="col-span-3 self-start sticky top-14">
            <SideBarMenu />
          </div>

          <div className="col-span-6 block mx-[4rem] my-[1rem] ">
            {children}
          </div>

          <div className="col-span-3 self-start sticky top-14">
            <Contact />
          </div>

        </div>
      </NotisProvider>
    </div>
  );
}
export default HomeLayouts;
