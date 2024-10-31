import { Drawer, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import { db } from "../firebase/config";
import React, { useEffect, useState, useContext, useRef } from "react";
import MyContact from "../config/MyContact";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import useFirestore from "../hooks/useFirestore";
import { faPaperPlane, faTimes } from "@fortawesome/free-solid-svg-icons";
import cookie from "react-cookies";
import {
  addDoc,
  getDocs,
  collection,
  query,
  where,
  orderBy,
  onSnapshot,
  updateDoc,
  doc,
  arrayContains,
} from "firebase/firestore";
import MyContext from "../config/MyContext";
import { NavLink } from "react-router-dom";

function ChatDrawer({ isOpenChat, onCloseChat, chatId }) {
  const [roomId, setRoomId] = useState();
  const [contact] = useContext(MyContact);
  const [user] = useContext(MyContext);
  const chatContacts = contact.find((c) => chatId === c.id);
  const [messages, setMessages] = useState([]);
  const messageListRef = useRef(null);
  const [messageContent, setMessageContent] = useState("");
  const [loading, setLoading] = useState(false); 

  const onClose = () => {
    onCloseChat();
    setMessages([]);
    setRoomId(null);
    console.log("Đóng");
  };

  const findRooms = async () => {
    try {
      setLoading(true); 
      const querySnapshot = await getDocs(
        query(
          collection(db, "rooms"),
          where("members", "array-contains", user.id)
        )
      );

      const room = {};
      querySnapshot.forEach((doc) => {
        if (doc.data().members.includes(chatId)) {
          room[doc.id] = doc.data();
        }
      });

      if (Object.keys(room).length === 0) {
        const createdAt = new Date();
        const newRoomRef = await addDoc(collection(db, "rooms"), {
          members: [user.id, chatId],
          createdAt: createdAt,
        });
        setRoomId(newRoomRef.id);
        loadRoomMessages(newRoomRef.id);
      } else {
        const roomId = Object.keys(room)[0];
        setRoomId(roomId);
        loadRoomMessages(roomId);
      }
    } catch (error) {
      console.error("Error getting documents: ", error);
    } finally {
      setLoading(false); // Kết thúc loading khi dữ liệu đã được tải xong
    }
  };

  const loadRoomMessages = (roomId) => {
    const roomRef = collection(db, `rooms/${roomId}/messages`);
    const q = query(roomRef, orderBy("createdAt"));

    const unsubscribe = onSnapshot(q, (snapshot) => {
      const newMessages = [];
      let previousDay = "";

      snapshot.forEach((doc) => {
        const data = doc.data();
        const createdAt = new Date(data.createdAt.toDate());
        const formattedTime = `${createdAt.getHours()}:${
          createdAt.getMinutes() < 10 ? "0" : ""
        }${createdAt.getMinutes()}`;
        const formattedDay = `${createdAt.getDate()}/${
          createdAt.getMonth() + 1
        }/${createdAt.getFullYear()}`;

        if (formattedDay !== previousDay) {
          newMessages.push({
            id: "date_" + doc.id,
            text: formattedDay,
            isDateSeparator: true,
          });
          previousDay = formattedDay;
        }

        newMessages.push({
          id: doc.id,
          text: data.content,
          userId: data.userId,
          createdAt: formattedTime,
          createdDay: formattedDay,
        });
      });
      setMessages(newMessages);
    });

    return unsubscribe;
  };

  useEffect(() => {
    if (isOpenChat && chatId) {
      findRooms();
    }
  }, [isOpenChat, chatId, user.id]);
  
  useEffect(() => {
    if (messageListRef.current) {
      messageListRef.current.scrollIntoView();
    }
  }, [messages]);
  
  
  const handleSentMessage = async (event) => {
    event.preventDefault();
    try {
      if (!roomId) {
        console.error("Room ID is not set.");
        return;
      }

      const createdAt = new Date();
      const docRef = await addDoc(collection(db, `rooms/${roomId}/messages`), {
        content: messageContent,
        userId: user.id,
        createdAt: createdAt,
      });
      setMessageContent("");
      const ownerData = {
        userId: user.id,
        firstName: user.firstName,
        lastName: user.lastName,
        avatar: user.avatar,
      };
      handleSentNoti(ownerData);
      console.log("Document written with ID: ", docRef.id);
    } catch (e) {
      console.error("Error adding document: ", e);
    }
  };

  const handleSentNoti = async (ownerData) => {
    try {
      const createdAt = new Date();
      const docRef = await addDoc(collection(db, `notis`), {
        content: `vừa gửi tin nhắn cho bạn`,
        fromId: ownerData,
        userId: chatId,
        refOf: chatId,
        action: "MESSAGE",
        createdAt: createdAt,
        is_read: false,
      });
      console.log("Document written with ID: ", docRef.id);
    } catch (e) {
      console.error("Error adding document: ", e);
    }
  };


  const handleInputChange = (event) => {
    setMessageContent(event.target.value);
  };

  if (chatContacts) {
    return (
      <Drawer
        title={
          <NavLink to={`/profile/${chatContacts.id}`}>
            <div className="flex items-center space-x-2">
              <img
                className="w-8 h-8 rounded-full"
                src={chatContacts.avatar}
                alt="Avatar"
              />
              <div>
                {chatContacts.firstName} {chatContacts.lastName}
              </div>
            </div>
          </NavLink>
        }
        placement="right"
        width={400}
        onClose={onClose}
        open={isOpenChat}
      >
        <div>
          <div className=" overflow-y-auto max-h-[100%] mb-20">
            {/* Chat messages */}
            <div className="flex flex-col space-y-2">
                {loading ? (
                  <Spin
                    indicator={
                      <LoadingOutlined
                        style={{
                          fontSize: 24,
                        }}
                        spin
                      />
                    }
                  />
                ) : (
                  messages.map((message, index) => (
                    <div
                      key={message.id}
                      className={`flex items-center ${
                        message.userId === user.id ? "justify-end" : ""
                      }`}
                    >
                      {message.isDateSeparator ? ( // Kiểm tra nếu đây là phần tử hiển thị ngày tháng năm
                        <div className="w-full text-gray-400 flex justify-center text-center text-sm my-2">
                          {message.text} {/* Hiển thị ngày tháng năm */}
                        </div>
                      ) : (
                        <div>
                          <div
                            className={`flex justify-between text-sm rounded-2xl px-3 py-1 ${
                              message.userId === user.id
                                ? "bg-blue-500 text-white"
                                : "bg-pink-500 text-white"
                            }`}
                          >
                            <div>{message.text}</div>
                            <div className="flex items-end ml-4 text-xs text-gray-300">
                              {message.createdAt}
                            </div>
                          </div>
                        </div>
                      )}
                    </div>
                  ))
                )}
              </div>
              <div ref={messageListRef}></div>
          </div>

          <div className="absolute z-40 bg-white bottom-0 left-0 p-4 w-full">
            <div className="">
              <form
                onSubmit={(event) => handleSentMessage(event)}
                className="flex bottom-0 items-center justify-between"
              >
                <input
                  type="text"
                  placeholder="Aa"
                  className="w-full px-2 pl-4 py-2 mr-2 rounded-2xl bg-gray-200 focus:outline-none"
                  value={messageContent}
                  onChange={handleInputChange} // Xử lý sự kiện thay đổi giá trị của input
                />
                <button type="submit">
                  <FontAwesomeIcon
                    className="cursor-pointer"
                    icon={faPaperPlane}
                    size="lg"
                    color="blue"
                  />
                </button>
              </form>
            </div>
          </div>
        </div>
      </Drawer>
    );
  }
}

export default ChatDrawer;
