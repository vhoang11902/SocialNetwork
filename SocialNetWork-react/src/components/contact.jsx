import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
} from "@fortawesome/free-solid-svg-icons";
import React,{useContext, useEffect, useState}  from "react";
import { Modal, Button } from 'antd';
import Apis, { authApi, endpoints } from "../utils/Apis";
import MyContact from "../config/MyContact";
import {  Drawer, Radio, Space } from 'antd';
import ChatDrawer from "./chatDrawer";
function Contact() {
  const [contact, setContact] = useContext(MyContact)
  const loadContacts = async () => {
    try {
      const response = await authApi().get(endpoints["friends"]);
      setContact(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    loadContacts();
  }, []);

  
  const [open, setOpen] = useState(false);
  const [chatId, setChatId] = useState();
  const showDrawer = (chatId) => {
    setChatId(chatId)
    setOpen(true);
  };
  const onClose = () => {
    setOpen(false);
  };


  return (
    <div className="">
      <div className=" h-full text-left py-4 px-2">
        <div className="text-gray-500">
          Người liên hệ
        </div>
        <div className="">
          <ul>
            {contact.map((c)=>(
              <li onClick={() => showDrawer(c.id)} key={c.id} className="flex px-2 py-2 my-1 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
                <div className="w-9 h-9 flex justify-center items-center rounded-full mr-2">
                <img src={c.avatar} className="w-9 h-9 rounded-full" alt="avatar" />
                </div>
                <div className="">{c.lastName} {c.firstName} </div>
                
            </li>
            ))}
            <ChatDrawer
              chatId={chatId}
              isOpenChat={open}
              onCloseChat={onClose}
            />
          </ul>
        </div>
        
      </div>

    </div>
  );
}

export default Contact;
