import React, { useContext, useState } from "react";
import axios from "axios";
import { Modal, Upload, Button, message, Switch } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImages,faFileSignature,faHome } from "@fortawesome/free-solid-svg-icons";
import Post from "../../components/post";
import cookie from "react-cookies";
import { authApi, endpoints } from "../../utils/Apis";
import MyContext from "../../config/MyContext";

function Home() {
  const [user] = useContext(MyContext);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [postContent, setPostContent] = useState("");
  const [fileList, setFileList] = useState([]);
  const [cmtStatus, setCmtStatus] = useState(true);
  const [postLoading, setPostLoading] = useState("");
  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const onChange = (checked) => {
    setCmtStatus(checked)
  };
  const handlePost = async (event) => {
    event.preventDefault();
    
    const formData = new FormData();
    formData.append("postContent", postContent);
    formData.append("postAudiance", 1);
    formData.append("postStatus", "READY");
    formData.append("commentStatus", cmtStatus)
  
    fileList.forEach((file) => {
      formData.append("images", file.originFileObj);
    });
    try {
      const response = await authApi().post(
        endpoints["add-post"],
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data", 
          },
        }
      );
  
      setPostLoading(response.data)
      setIsModalOpen(false);
      setPostContent("");
      setFileList([]);
    } catch (error) {
      console.error("Failed to upload post:", error);
      message.error("Đã xảy ra lỗi khi tải lên bài viết");
    }
  };
  const handleChange = ({ fileList }) => {
    setFileList(fileList);
  };

  return (
    <div className="">
      <div className="w-full py-2 px-4 my-4 rounded-lg bg-white shadow-lg">
        <div className="flex py-2 items-center justify-center border-b border-gray-200">
        <div className="w-11 h-10 flex justify-center items-center rounded-full  mr-2">
          <img src={user.avatar} className="w-10 h-10 rounded-full" alt="Logo" />
          </div>
          <div
            onClick={showModal}
            className="w-full py-2 pl-4 text-lg text-start text-gray-500 rounded-2xl hover:bg-gray-200 transition-all cursor-pointer"
          >
            {user.firstName} ơi, Bạn đang nghĩ gì vậy
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
              <div className="flex gap-2 mb-2">
                  <span>Cho phép bình luận: </span>
                  <Switch defaultChecked onChange={onChange} />
                </div>
              <div className="flex mb-2">
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
      <Post postLoading={postLoading}/>
    </div>
  );
}

export default Home;
