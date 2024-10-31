import React, { useContext, useState, useEffect, useMemo } from "react";
import { useParams } from "react-router-dom";
import { message, Modal, Upload, Button } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faImages,
  faFileSignature,
  faHome,
  faUserPlus,
  faUserCheck,
  faMessage,
  faSliders,
} from "@fortawesome/free-solid-svg-icons";
import ChatDrawer from "../../components/chatDrawer";
import MyContext from "../../config/MyContext";
import MyContact from "../../config/MyContact";
import MyRequest from "../../config/MyRequest";
import TimelinePost from "../../components/timeLine";
import { Popover } from "antd";
import { authApi, endpoints } from "../../utils/Apis";

function Profile() {
  const { id } = useParams();
  const [user] = useContext(MyContext);
  const [users, setUser] = useState();
  const [contact] = useContext(MyContact);
  const [request] = useContext(MyRequest);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [postContent, setPostContent] = useState("");
  const [fileList, setFileList] = useState([]);
  const [postLoading, setPostLoading] = useState("");
  const [requestSend, setRequestSend] = useState([]);
  const [status, setStatus] = useState(null);

  const loadRequestFriends = async () => {
    try {
      const response = await authApi().get(endpoints["requestSendList"]);
      setRequestSend(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const loadProfile = async () => {
    try {
      const response = await authApi().get(endpoints["profile"](id));
      setUser(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    loadRequestFriends();
    loadProfile();
  }, [id]);
  const isFriend = contact.some((c) => c.id == id);
  const isRequestReceipt = request.some((c) => c.userId.id == id);
  const isRequestSend = requestSend.some((r) => r.connectionId.id == id);
  const statusMemo = useMemo(() => {
    if (isFriend) return "FRIEND";
    if (isRequestSend) return "REQSEND";
    if (isRequestReceipt) return "REQRECEIPT";
    return null;
  }, [status]);

  useEffect(() => {
    setStatus(statusMemo);
  }, [statusMemo]);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const handlePost = async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append("postContent", postContent);
    formData.append("postAudiance", 1);
    formData.append("postStatus", "READY");

    fileList.forEach((file) => {
      formData.append("images", file.originFileObj);
    });
    try {
      const response = await authApi().post(endpoints["add-post"], formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      console.log("Post uploaded successfully:", response.data);
      message.success("Bài viết đã được tải lên thành công");
      setPostLoading(response.data);
      setIsModalOpen(false);
      setPostContent("");
      setFileList([]);
    } catch (error) {
      console.error("Failed to upload post:", error);
      message.error("Đã xảy ra lỗi khi tải lên bài viết");
    }
  };

  const handleAcceptRequest = async (event) => {
    const idRequestReceipt = request.find((c) => c.userId.id == id);

    try {
      const response = await authApi().put(
        endpoints["acceptRequest"](idRequestReceipt.id),
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("Request accepted successfully:", response.data);
      message.success("Đồng ý kết bạn thành công");
      setStatus("FRIEND");
    } catch (error) {
      console.error("Failed to accept request:", error);
      message.error("Đã xảy ra lỗi khi đồng ý kết bạn");
    }
  };

  const handleChange = ({ fileList }) => {
    setFileList(fileList);
  };

  const [open, setOpen] = useState(false);

  const [chatId, setChatId] = useState();
  const showDrawer = () => {
    console.log("Mở");
    setChatId(id);
    setOpen(true);
  };
  const onClose = () => {
    setOpen(false);
  };

  const friendOpt = (commentId, postId) => (
    <div className="">
      <div
        className="px-3 py-1 hover:bg-gray-300 hover:transition-all rounded-sm cursor-pointer"
        onClick={(event) => handleDeleteFriend(commentId)}
      >
        Huỷ kết bạn
      </div>
    </div>
  );

  const handleDeleteFriend = async (event) => {
    try {
      const response = await authApi().put(
        endpoints["deleteFriend"](id),
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("Request accepted successfully:", response.data);
      message.success("Huỷ bạn bè thành công");
      setStatus(" ");
    } catch (error) {
      console.error("Failed to accept request:", error);
      message.error("Đã xảy ra lỗi khi đồng ý kết bạn");
    }
  };


  const handleSendFriendRequest = async (event) => {
    try {
      const response = await authApi().post(
        endpoints["sendRequest"](id),
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("Request accepted successfully:", response.data);
      message.success("Gửi lời kết bạn thành công");
      setStatus("REQSEND");
    } catch (error) {
      console.error("Failed to accept request:", error);
      message.error("Đã xảy ra lỗi khi đồng ý kết bạn");
    }
  };
  if(users){
    return (
      <div className="">
        <ChatDrawer chatId={chatId} isOpenChat={open} onCloseChat={onClose} />
        <div className="bg-white flex flex-col items-center">
          <div className="w-[85%]">
            <div className="w-full bg-gray-200 rounded-b-lg rounded-t-none">
              <div className="flex align-middle justify-center items-center">
                <img
                  src={users.coverImage}
                  className="h-[400px] max-h-[400px]"
                  alt="Cover"
                />
              </div>
            </div>
            <div className="flex justify-between mx-7">
              <div className="w-full flex">
                <div className="h-36 w-44 relative mr-4">
                  <div className="absolute bg-white block z-10 bottom-[0px] rounded-full p-1">
                    <img
                      src={users.avatar}
                      className="rounded-full h-44 w-44"
                      alt="Avatar"
                    />
                  </div>
                </div>
                <div className="flex-grow text-start mt-9 mb-4">
                  <div className="text-3xl">
                    {users.lastName} {users.firstName}
                  </div>
                  <div className="text-xl text-gray-500">
                    {contact.length} connections
                  </div>
                </div>
              </div>
              <div className="w-[80%] col-span-5 flex text-start justify-end mt-9 mb-4">
                {status === null && (
                  <div className="" onClick={(e) => handleSendFriendRequest(e)}>
                    <div className="font-semibold mx-2 px-5 py-2 rounded-lg cursor-pointer hover:bg-gray-400 bg-gray-300">
                      <span className="mr-1">
                        <FontAwesomeIcon icon={faUserPlus} size="sm" />
                      </span>
                      <span>Thêm bạn bè</span>
                    </div>
                  </div>
                )}
                {status === "REQRECEIPT" && (
                  <div className="" onClick={(e) => handleAcceptRequest(e)}>
                    <div className="font-semibold mx-2 px-5 py-2 rounded-lg cursor-pointer hover:bg-gray-400 bg-gray-300">
                      <span className="mr-1">
                        <FontAwesomeIcon icon={faUserPlus} size="sm" />
                      </span>
                      <span>Chấp nhận bạn bè</span>
                    </div>
                  </div>
                )}
                {status === "REQSEND" && (
                  <div className="">
                  <Popover content={friendOpt()} trigger="click" placement="bottom">
                    <div className="font-semibold mx-2 px-5 py-2 rounded-lg cursor-pointer hover:bg-gray-400 bg-gray-300">
                      <span className="mr-1">
                        <FontAwesomeIcon icon={faUserPlus} size="sm" />
                      </span>
                      <span>Đã gửi lời kết bạn</span>
                    </div>
                    </Popover> 
                  </div>
                )}
                {status === "FRIEND" && (
                  <div>
                  <Popover content={friendOpt()} trigger="click" placement="bottom">
                    <div className="font-semibold mx-2 px-5 py-2 rounded-lg cursor-pointer hover:bg-gray-400 bg-gray-300">
                      <span className="mr-1">
                        <FontAwesomeIcon icon={faUserCheck} size="sm" />
                      </span>
                      <span>Bạn Bè</span>
                    </div>
                  </Popover>
                  </div>
                  
                )}
                <div className="">
                  <div className="font-semibold mx-2 px-5 py-2 rounded-lg cursor-pointer hover:bg-blue-600 bg-blue-500 text-white">
                    <span className="mr-1">
                      <FontAwesomeIcon icon={faMessage} size="sm" />
                    </span>
                    <span>Message</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="mx-80">
          <div className="w-full py-2 px-4 my-4 rounded-lg bg-white shadow-lg">
            <div className="flex py-2 items-center justify-center border-b border-gray-200">
              <div className="w-11 h-10 flex justify-center items-center rounded-full bg-red-400 mr-2">
                <FontAwesomeIcon icon={faHome} size="md" />
              </div>
              <div
                onClick={showModal}
                className="w-full py-2 pl-6 text-lg text-start text-gray-500 rounded-2xl hover:bg-gray-200 transition-all cursor-pointer"
              >
                Hoang ơi, Bạn đang nghĩ gì vậy
              </div>
              <Modal
                footer={null}
                visible={isModalOpen}
                onOk={handleOk}
                onCancel={handleCancel}
              >
                <div className="text-center font-semibold text-lg mb-4">
                  Tạo bài viết
                </div>
                <div className="border-t border-gray-400 py-2">
                  <div className="my-2">
                    <textarea
                      className="w-full h-[6rem] focus:outline-none"
                      placeholder="Hoàng ơi, Bạn đang nghĩ gì đó?"
                      value={postContent}
                      onChange={(e) => setPostContent(e.target.value)}
                    ></textarea>
                  </div>
                  <div>
                    <Upload
                      action="YOUR_UPLOAD_API_ENDPOINT"
                      listType="picture"
                      fileList={fileList}
                      onChange={handleChange}
                      beforeUpload={() => false}
                    >
                      <Button icon={<UploadOutlined />}>Tải lên ảnh</Button>
                    </Upload>
                  </div>
                </div>
                <div>
                  <button
                    onClick={handlePost}
                    className="bg-primary-600 text-white w-full mt-2 py-2 px-5 rounded-xl hover:bg-primary-700 transition"
                  >
                    Đăng bài viết
                  </button>
                </div>
              </Modal>
            </div>
            <div className="flex py-2 text-gray-500 text-lg">
              <div className="w-full flex justify-center py-2 cursor-pointer">
                <div className="mx-2">
                  <FontAwesomeIcon icon={faImages} size="lg" color="green" />
                </div>
                <div>Ảnh/Video</div>
              </div>
              <div className="w-full flex justify-center py-2 cursor-pointer">
                <div className="mx-2">
                  <FontAwesomeIcon icon={faFileSignature} size="lg" color="red" />
                </div>
                <div>Khảo Sát</div>
              </div>
            </div>
          </div>
          <div className="w-full flex items-center justify-between py-2 px-4 my-4 rounded-lg bg-white shadow-lg ">
            <div className="text-xl font-semibold">Bài viết</div>
            <div className="bg-gray-200 px-6 py-2 rounded-lg cursor-pointer hover:bg-gray-300 transition-all">
              <span className="mr-2">
                <FontAwesomeIcon icon={faSliders} size="sm" />
              </span>
              <span>Bộ lọc</span>
            </div>
          </div>
          <TimelinePost postLoading={postLoading} userId={id} />
        </div>
      </div>
    );
  }

}

export default Profile;
