import { Drawer, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import { db } from "../firebase/config";
import React, { useEffect, useState, useContext, useRef } from "react";
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

function NotiDrawer() {
  const { notis, loading } = useContext(NotisContext);
  const handleUpdateReadNoti = async (notiId) => {
    try {
      const docRef = doc(db, "notis", notiId);
      await updateDoc(docRef, {
        is_read: true,
      });
      console.log("Document updated successfully!");
    } catch (e) {
      console.error("Error updating document: ", e);
    }
  };
  return (
    <div className="max-w-80 ">
      <div className="font-bold text-lg mb-2 border-b-2">Thông báo</div>
      {loading ? (
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
          {notis.map((noti) => (
            <div
              onClick={(event) => handleUpdateReadNoti(noti.id)}
              key={noti.id}
              className={`flex items-center justify-between px-2 py-2 mb-2 cursor-pointer rounded-lg border-2 ${
                noti.is_read ? "" : "bg-gray-200"
              }`}
            >
              <div className="w-full flex items-center">
                <div className="w-[20%] flex justify-center items-center rounded-full mr-2 ">
                  <img
                    src={noti.from.avatar}
                    className="w-14 h-14 rounded-full"
                    alt="Avatar"
                  />
                </div>
                <div className="w-[70%] ">
                  <div className="">
                    <span className="font-bold">
                      {noti.from.lastName} {noti.from.firstName}{" "}
                    </span>
                    <span>{noti.content}</span>
                  </div>
                  <Moment
                    className="text-sm font-light text-gray-500"
                    locale="vi"
                    fromNow
                  >
                    {noti.createdAt}
                  </Moment>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default NotiDrawer;
