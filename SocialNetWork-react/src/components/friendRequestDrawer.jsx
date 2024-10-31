import { Drawer, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import { db } from "../firebase/config";
import React, { useEffect, useState, useContext, useRef } from "react";
import Apis, { authApi, endpoints } from "../utils/Apis";
import Moment from "react-moment";
import MyContext from "../config/MyContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import useFirestore from "../hooks/useFirestore";
import { faPaperPlane, faTimes } from "@fortawesome/free-solid-svg-icons";
import cookie from "react-cookies";
import {
  updateDoc,
  doc,
} from "firebase/firestore";
import { NotisContext } from "../config/noti";
import { NavLink } from "react-router-dom";
import MyRequest from "../config/MyRequest";

function RequestDrawer() {
  const [request, setRequests] = useContext(MyRequest)
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true)
    const loadRequestFriends = async () => {
        try {
          const response = await authApi().get(endpoints["requestReceiptList"]);
          setRequests(response.data)
        } catch (error) {
          console.log(error);
        } finally {
          setIsLoading(false);
        }
      };
    loadRequestFriends();
  }, []);

  


  return (
    <div className="max-w-80 ">
      <div className="font-bold text-lg mb-2 border-b-2">Lời mời kết bạn</div>
      {isLoading ? (
        <div className="w-80 flex items-center justify-center">
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
        </div>
      ) : (
        <div style={{ maxHeight: "500px", overflowY: "auto" }}>
          {request.map((r) => (
            <NavLink
            to={`/profile/${r.userId.id}`}
              key={r.userId.id}
              className={`flex items-center justify-between px-2 py-2 mb-2 cursor-pointer rounded-lg border-2`}
            >
              <div className="w-full flex items-center">
                <div className="w-[20%] flex justify-center items-center rounded-full mr-2 ">
                  <img
                    src={r.userId.avatar}
                    className="w-14 h-14 rounded-full"
                    alt="Avatar"
                  />
                </div>
                <div className="w-[70%] ">
                  <div className="">
                    <span className="font-bold">
                      {r.userId.lastName} {r.userId.firstName}{" "}
                    </span>
                    <span>Vừa gửi lời mời kết bạn tới bạn.</span>
                  </div>
                  {/* <Moment
                    className="text-sm font-light text-gray-500"
                    locale="vi"
                    fromNow
                  >
                    {noti.createdAt}
                  </Moment> */}
                </div>
              </div>
            </NavLink>
          ))}
        </div>
      )}
    </div>
  );
}

export default RequestDrawer;
