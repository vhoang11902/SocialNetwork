import Header from "./HomeLayouts/Header/header";
import Footer from "./HomeLayouts/Footer/footer";
import MyContext from "../config/MyContext";
import { useState } from "react";
import { NotisProvider } from "../config/noti";
function ProfileLayouts({ children }) {
  const [Contacts, setContacts] = useState([]);

  return (
    <div className="font-body bg-gray-100">
    <NotisProvider>
      <Header className=""/>
        <div className=" justify-center block">
          <div className="w-full z-0">{children}</div>
        </div>
    </NotisProvider>
    </div>
  );
}
export default ProfileLayouts;
