import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faXmark,
  faHeart,
  faFaceLaughBeam,
  faThumbsUp,
  faEllipsis,
  faEarthAsia,
  faPaperPlane,
} from "@fortawesome/free-solid-svg-icons";
import { Drawer, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
import {
  addDoc,
  collection,
  where,
  getDocs,
  query,
  deleteDoc,
  doc,
} from "firebase/firestore";
import { db } from "../firebase/config";
import { Button, Popover, Space, Tooltip } from "antd";
import { faComment } from "@fortawesome/free-regular-svg-icons";
import { useState, useEffect, useMemo, useContext } from "react";
import Apis, { authApi, endpoints } from "../utils/Apis";
import MyContext from "../config/MyContext";
import cookie from "react-cookies";
import Moment from "react-moment";
import { NavLink, useParams } from "react-router-dom";
function Post({ postLoading }) {
  const [user] = useContext(MyContext);
  const [post, setPost] = useState([]);
  const [content, setContent] = useState();
  const [editCmtId, setEditCmtId] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [arrow, setArrow] = useState("Show");
  const ownerData = {
    userId: user.id,
    firstName: user.firstName,
    lastName: user.lastName,
    avatar: user.avatar,
  };
  const iconReaction = (id) => (
    <div className="flex gap-2">
      <div
        onClick={(event) => handleDropAction(id, "LIKE", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-blue-600 shadow-inner cursor-pointer rounded-full transition bg-blue-400 "
      >
        <FontAwesomeIcon icon={faThumbsUp} size="xl" color="white" />
      </div>
      <div
        onClick={(event) => handleDropAction(id, "LOVE", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-red-600 shadow-inner cursor-pointer rounded-full transition bg-red-400"
      >
        <FontAwesomeIcon icon={faHeart} size="xl" color="white" />
      </div>
      <div
        onClick={(event) => handleDropAction(id, "HAHA", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-yellow-600 shadow-inner cursor-pointer rounded-full transition bg-yellow-400"
      >
        <FontAwesomeIcon icon={faFaceLaughBeam} size="xl" color="white" />
      </div>
    </div>
  );
  const iconUpdateReaction = (postId, id) => (
    <div className="flex gap-2">
      <div
        onClick={(event) => handleUpdateAction(postId, id, "LIKE", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-blue-600 shadow-inner cursor-pointer rounded-full transition bg-blue-400 "
      >
        <FontAwesomeIcon icon={faThumbsUp} size="xl" color="white" />
      </div>
      <div
        onClick={(event) => handleUpdateAction(postId, id, "LOVE", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-red-600 shadow-inner cursor-pointer rounded-full transition bg-red-400"
      >
        <FontAwesomeIcon icon={faHeart} size="xl" color="white" />
      </div>
      <div
        onClick={(event) => handleUpdateAction(postId, id, "HAHA", event)}
        className=" flex items-center justify-center px-[8px] py-[8px] hover:bg-yellow-600 shadow-inner cursor-pointer rounded-full transition bg-yellow-400"
      >
        <FontAwesomeIcon icon={faFaceLaughBeam} size="xl" color="white" />
      </div>
    </div>
  );

  const mergedArrow = useMemo(() => {
    if (arrow === "Hide") {
      return false;
    }

    if (arrow === "Show") {
      return true;
    }

    return {
      pointAtCenter: true,
    };
  }, [arrow]);

  const loadPosts = async (page) => {
    try {
      const response = await authApi().get(endpoints["posts"](page));
      if (page === 1) {
        setPost(response.data);
      } else {
        setPost((prevPosts) => [...prevPosts, ...response.data]);
        setPage(page); // Giả định response.data là một mảng các bài viết
        console.log(post);
      }
      console.log(page);
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    setIsLoading(true);
    loadPosts(page);
  }, [page, postLoading]);

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const handleScroll = () => {
    if (
      window.innerHeight + document.documentElement.scrollTop ===
      document.documentElement.offsetHeight
    ) {
      setPage((prevPage) => prevPage + 1);
    }

    // Kiểm tra nếu trang đã được kéo lên đầu trở lại thì set page thành 1
    if (document.documentElement.scrollTop === 0) {
      setPage(1);
    }
  };

  const addComment = async (postId, event) => {
    event.preventDefault();
    try {
      const response = await authApi().post(endpoints["add-comment"], {
        content: content,
        post: postId,
      });
      const newComment = response.data;
      setContent("");
      setPost((prevPosts) => {
        const updatedPosts = prevPosts.map((p) => {
          if (p.id === postId) {
            return {
              ...p,
              activeComments: [...p.activeComments, newComment],
            };
          }
          return p;
        });
        return updatedPosts;
      });
      const postOwner = post.find((p) => p.id === postId)?.userId;
      handleSentNoti(
        ownerData,
        postOwner.id,
        postId,
        "vừa mới thêm một bình luận vào bài viết của bạn",
        "COMMENT"
      );
    } catch (error) {
      console.log(error);
    }
  };
  const handleSentNoti = async (owner, toId, postId, content, action) => {
    try {
      const createdAt = new Date();
      const docRef = await addDoc(collection(db, `notis`), {
        content: content,
        fromId: owner,
        userId: toId,
        refOf: postId,
        action: action,
        createdAt: createdAt,
        is_read: false,
      });
      console.log("Document written with ID: ", docRef.id);
    } catch (e) {
      console.error("Error adding document: ", e);
    }
  };
  const handleDeleteNoti = async (owner, toId, postId, content, action) => {
    try {
      const createdAt = new Date();
      const querySnapshot = await getDocs(
        query(
          collection(db, "notis"),
          where("action", "==", "REACTION"),
          where("refOf", "==", postId),
          where("fromId.userId", "==", owner.userId)
        )
      );
      const ids = querySnapshot.docs.map((doc) => doc.id);
      try {
        const deletePromises = ids.map((id) => deleteDoc(doc(db, "notis", id)));
        await Promise.all(deletePromises);
        console.log("Documents deleted successfully");
      } catch (error) {
        console.error("Error deleting documents: ", error);
      }
    } catch (e) {
      console.error("Error adding document: ", e);
    }
  };

  const handleSubmitEditComment = async (commentId, postId, event) => {
    event.preventDefault();
    try {
      const response = await authApi().post(endpoints["add-comment"], {
        id: commentId,
        content: content,
        post: postId,
      });
      const updatedComment = response.data;
      setContent("");
      setEditCmtId(null);

      setPost((prevPosts) =>
        prevPosts.map((p) => {
          if (p.id === postId) {
            const oldComment = p.activeComments.filter(
              (c) => c.id !== commentId
            );
            return {
              ...p,
              activeComments: [...oldComment, updatedComment],
            };
          }
          return p;
        })
      );
      setIsEditing(false);
    } catch (error) {
      console.log(error);
    }
  };

  const commentOpt = (commentId, postId) => (
    <div className="w-36">
      <div
        className="px-3 py-1 hover:bg-gray-300 hover:transition-all rounded-sm cursor-pointer"
        onClick={(event) => handleDeleteComment(commentId)}
      >
        Delete comment
      </div>
      <div
        className="px-3 py-1 hover:bg-gray-300 hover:transition-all rounded-sm cursor-pointer"
        onClick={(event) => handleEditComment(commentId)}
      >
        Edit comment
      </div>
    </div>
  );

  const handleDropAction = async (postId, action, event) => {
    event.preventDefault();
    try {
      const response = await authApi().post(
        endpoints["add-reaction"](postId, action)
      );
      const newReaction = response.data;
      setPost((prevPosts) => {
        const updatedPosts = prevPosts.map((p) => {
          if (p.id === postId) {
            return {
              ...p,
              activeReactions: [...p.activeReactions, newReaction],
            };
          }
          return p;
        });
        return updatedPosts;
      });
      const postOwner = post.find((p) => p.id === postId)?.userId;
      handleSentNoti(
        ownerData,
        postOwner.id,
        postId,
        `vừa ${action} bài viết của bạn`,
        "REACTION"
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleUpdateAction = async (postId, reactionId, action, event) => {
    event.preventDefault();
    try {
      const response = await authApi().put(
        endpoints["update-reaction"](postId, reactionId, action)
      );
      const updatedReaction = response.data;

      setPost((prevPosts) =>
        prevPosts.map((p) => {
          if (p.id === postId) {
            const oldReaction = p.activeReactions.filter(
              (r) => r.id !== reactionId
            );
            return {
              ...p,
              activeReactions: [...oldReaction, updatedReaction],
            };
          }
          return p;
        })
      );

      // Gửi thông báo
      const postOwner = post.find((p) => p.id === postId)?.userId;
      handleSentNoti(
        ownerData,
        postOwner.id,
        postId,
        `vừa ${action} bài viết của bạn`,
        "REACTION"
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleDeleteAction = async (postId, reactionId, event) => {
    event.preventDefault();
    try {
      const response = await authApi().delete(
        endpoints["delete-reaction"](postId, reactionId)
      );
      console.log(response.data);
      const postOwner = post.find((p) => p.id === postId)?.userId;
      handleDeleteNoti(
        ownerData,
        postOwner.id,
        postId,
        `vừa  bài viết của bạn`,
        "REACTION"
      );
      setPost((prevPosts) =>
        prevPosts.map((p) => ({
          ...p,
          activeReactions: p.activeReactions.filter((r) => r.id !== reactionId),
        }))
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleDeleteComment = async (commentId) => {
    try {
      const response = await authApi().put(
        endpoints["delete-comment"](commentId)
      );
      console.log("thanh cong");
      setPost((prevPosts) =>
        prevPosts.map((p) => ({
          ...p,
          activeComments: p.activeComments.filter((c) => c.id !== commentId),
        }))
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleEditComment = async (commentId) => {
    setEditCmtId(commentId); // Đặt id của comment đang được chỉnh sửa
    setIsEditing(true);
  };
  const cancelEditCmt = async (commentId) => {
    setEditCmtId(null); // Đặt id của comment đang được chỉnh sửa
    setIsEditing(false); // Thiết lập trạng thái chỉnh sửa là true
  };

  return (
    <div className="">
      {post.map((p) => (
        <div key={p.id} className="w-full my-4 py-2 rounded-lg bg-white shadow-lg">
          <div className="flex justify-between px-4">
            <div className="flex items-center">
              <div className="w-10 h-10 flex justify-center items-center rounded-full mr-2">
                <img src={p.userId.avatar} className="w-10 h-10 rounded-full" />
              </div>
              <div className="text-left">
                <NavLink className="font-semibold"
                  to={`/profile/${p.userId.id}`}>
                  {p.userId.lastName + " " + p.userId.firstName}
                </NavLink>
                <div className="flex text-sm text-gray-400">
                  <Moment locale="vi" fromNow>
                    {p.createdDate}
                  </Moment>
                  <div className="mx-2">
                    <FontAwesomeIcon icon={faEarthAsia} size="md" />
                  </div>
                </div>
              </div>
            </div>

            <div className="flex">
              <div className="mx-2 cursor-pointer">
                <FontAwesomeIcon icon={faEllipsis} size="xl" />
              </div>
              <div className="mx-2 cursor-pointer">
                <FontAwesomeIcon icon={faXmark} size="xl" />
              </div>
            </div>
          </div>

          <div className="my-2 px-4 text-left">{p.postContent}</div>
          {/* image */}
          {p.images.map((img) => (
            <div key={img.id} className="flex justify-center my-2 ">
              <img
                // loading="lazy"
                height="765"
                width="526"
                src={img.imageUrl}
              ></img>
            </div>
          ))}
          {/* <div className="flex justify-center my-2 bg-gray-300">
            <img
              loading="lazy"
              height="765"
              width="526"
              src={p.image}
            ></img>
          </div> */}

          <div className="my-2 px-4 flex justify-between items-center text-left text-gray-500 text-base font-light">
            <div className="flex items-center ">
              {p.activeReactions && (
                <>
                  <div className="flex mr-2">
                    {p.activeReactions.find(
                      (reaction) => reaction.action === "LIKE"
                    ) && (
                      <div className=" flex items-center justify-center mr-[-4px] px-[4px] py-[4px] shadow-inner rounded-full bg-blue-400">
                        <FontAwesomeIcon
                          icon={faThumbsUp}
                          size="xs"
                          color="white"
                        />
                      </div>
                    )}
                    {p.activeReactions.find(
                      (reaction) => reaction.action === "LOVE"
                    ) && (
                      <div className=" flex items-center justify-center mr-[-4px] px-[4px] py-[4px] shadow-inner rounded-full bg-red-400">
                        <FontAwesomeIcon
                          icon={faHeart}
                          size="xs"
                          color="white"
                        />
                      </div>
                    )}
                    {p.activeReactions.find(
                      (reaction) => reaction.action === "HAHA"
                    ) && (
                      <div className=" flex items-center justify-center mr-[-4px] px-[4px] py-[4px] shadow-inner rounded-full bg-yellow-400">
                        <FontAwesomeIcon
                          icon={faFaceLaughBeam}
                          size="xs"
                          color="white"
                        />
                      </div>
                    )}
                  </div>
                  <div>
                    {p.activeReactions != "" ? (
                      <>
                        {!(p.activeReactions.length > 1) ? (
                          <>
                            {p.activeReactions.find(
                              (reaction) => reaction.user.id === user.id
                            ) && (
                              <div className="text-sm">
                                {user.lastName + " " + user.firstName}
                              </div>
                            )}
                          </>
                        ) : (
                          <>
                            {p.activeReactions.find(
                              (reaction) => reaction.user.id === user.id
                            ) && (
                              <div className="text-sm">
                                Bạn và {p.activeReactions.length - 1} người khác
                              </div>
                            )}
                          </>
                        )}

                        {!p.activeReactions.find(
                          (reaction) => reaction.user.id === user.id
                        ) && (
                          <div className="text-sm">
                            {p.activeReactions.length} người đã phản ứng
                          </div>
                        )}
                      </>
                    ) : null}
                  </div>
                  {/* {p.activeReactions.map((reaction) => (
                    <div key={reaction.id}>{reaction.user.firstName}</div>
                  ))} */}
                </>
              )}
            </div>
            <div className="">{p.activeComments.length} comments</div>
          </div>

          <div className="my-2 mx-4 flex text-left border-y border-gray-200">
            {!p.activeReactions.find(
              (reaction) => reaction.user.id === user.id
            ) ? (
              <Tooltip
                placement="top"
                color="white"
                title={iconReaction(p.id)}
                arrow={mergedArrow}
                overlayInnerStyle={{
                  backgroundColor: "white",
                  borderRadius: "30px",
                }}
              >
                <div
                  onClick={(event) => handleDropAction(p.id, "LIKE", event)}
                  className="flex p-2 w-full justify-center cursor-pointer hover:bg-gray-200 hover:transition-all  "
                >
                  <div className="mx-2">
                    <FontAwesomeIcon icon={faThumbsUp} size="lg" color="gray" />
                  </div>
                  <div className="text-gray-600">Like</div>
                </div>
              </Tooltip>
            ) : (
              <Tooltip
                placement="top"
                color="white"
                title={iconUpdateReaction(
                  p.id,
                  p.activeReactions.find(
                    (reaction) => reaction.user.id === user.id
                  ).id
                )}
                arrow={mergedArrow}
                overlayInnerStyle={{
                  backgroundColor: "white",
                  borderRadius: "30px",
                }}
              >
                <div
                  onClick={(event) =>
                    handleDeleteAction(
                      p.id,
                      p.activeReactions.find(
                        (reaction) => reaction.user.id === user.id
                      ).id,
                      event
                    )
                  }
                  className="flex p-2 w-full justify-center cursor-pointer hover:bg-gray-200 hover:transition-all  "
                >
                  <>
                    {p.activeReactions.find(
                      (reaction) =>
                        reaction.action === "LIKE" &&
                        reaction.user.id === user.id
                    ) && (
                      <div className="flex">
                        <div className="mx-2">
                          <FontAwesomeIcon
                            icon={faThumbsUp}
                            size="lg"
                            color="blue"
                          />
                        </div>
                        <div className="text-blue-600">Like</div>
                      </div>
                    )}
                    {p.activeReactions.find(
                      (reaction) =>
                        reaction.action === "LOVE" &&
                        reaction.user.id === user.id
                    ) && (
                      <div className="flex">
                        <div className="mx-2">
                          <FontAwesomeIcon
                            icon={faHeart}
                            size="lg"
                            color="red"
                          />
                        </div>
                        <div className="text-red-600">Yêu Thích</div>
                      </div>
                    )}
                    {p.activeReactions.find(
                      (reaction) =>
                        reaction.action === "HAHA" &&
                        reaction.user.id === user.id
                    ) && (
                      <div className="flex">
                        <div className="mx-2">
                          <FontAwesomeIcon
                            icon={faFaceLaughBeam}
                            size="lg"
                            color="#FFD43B"
                          />
                        </div>
                        <div className="text-yellow-500">Haha</div>
                      </div>
                    )}
                  </>
                </div>
              </Tooltip>
            )}

            <div className="flex p-2 w-full justify-center cursor-pointer hover:bg-gray-200 hover:transition-all ">
              <div className="mx-2">
                <FontAwesomeIcon icon={faComment} size="lg" color="gray" />
              </div>
              <div className="text-gray-600">Comment</div>
            </div>
          </div>
          {p.commentStatus ? (
            <div className="my-2 mx-4 text-left">
              {/* Comment */}
              {p.activeComments
                .sort(
                  (a, b) => new Date(a.createdDate) - new Date(b.createdDate)
                )
                .map((c) => (
                  <div className="flex mb-2">
                    <div className="w-10 h-10 flex justify-center items-center rounded-full bg-red-400 mr-2">
                      <FontAwesomeIcon icon={faHome} size="md" />
                    </div>
                    <div className="">
                      <div className="flex justify-between bg-gray-200 rounded-2xl px-3 py-2">
                        {editCmtId === c.id && isEditing ? ( // Kiểm tra xem comment này có đang được chỉnh sửa không
                          <>
                            <form
                              className="w-full relative"
                              onSubmit={(event) =>
                                handleSubmitEditComment(c.id, p.id, event)
                              }
                            >
                              <div className="flex ">
                                <input
                                  className="rounded-3xl border border-gray-300 h-8 w-full pl-4 p-5 bg-gray-200 focus:outline-none"
                                  placeholder={c.content} 
                                  name="keywords_submit"
                                  type="text"
                                  value={content}
                                  onChange={(e) => setContent(e.target.value)}
                                />
                                <button
                                  className="absolute z-20 right-0 h-[40px] w-[50px] "
                                  type="submit"
                                  name="search"
                                  value="valueSearch"
                                >
                                  <FontAwesomeIcon
                                    icon={faPaperPlane}
                                    size="md"
                                  />
                                </button>
                              </div>
                              <div
                                className="text-right text-sm cursor-pointer text-red-400"
                                onClick={(event) => cancelEditCmt()}
                              >
                                Huỷ
                              </div>
                            </form>
                          </>
                        ) : (
                          <>
                            <div>
                              <div className="text-xs font-bold">
                                {c.user.lastName + " " + c.user.firstName}
                              </div>
                              <div className="text-xs">{c.content}</div>
                            </div>

                            <Popover
                              content={commentOpt(c.id, p.id)}
                              trigger="click"
                            >
                              <FontAwesomeIcon
                                className="cursor-pointer"
                                icon={faEllipsis}
                                size="sm"
                              />
                            </Popover>
                          </>
                        )}
                        {/* )} */}
                      </div>
                      <div className="">
                        <ul className="text-xs text-gray-500 flex">
                          <li className="mx-1">
                            <Moment locale="vi" fromNow>
                              {c.createdDate}
                            </Moment>
                          </li>
                          <li className="mx-1 font-bold cursor-pointer">
                            Like
                          </li>
                          <li className="mx-1 font-bold cursor-pointer">
                            Phản hồi
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                ))}
              {/* Upload Comment */}
              <div className="flex mt-2">
                <div className="w-11 h-10 flex justify-center items-center rounded-full">
                  <img
                    src={user.avatar}
                    className="w-10 h-10 rounded-full"
                  />
                </div>
                <div className="w-full ">
                  <form
                    className="w-full relative"
                    onSubmit={(event) => addComment(p.id, event)}
                  >
                    <div className="flex ">
                      <input
                        className="rounded-3xl ml-2 h-8 w-full pl-4 p-5 bg-gray-200 focus:outline-none"
                        placeholder="Viết bình luận..."
                        name="keywords_submit"
                        type="text"
                        value={content}
                        onChange={(e) => setContent(e.target.value, p.id)}
                      />
                      <button
                        className="absolute z-20 right-0 h-[40px] w-[50px] "
                        type="submit"
                        name="search"
                        value="valueSearch"
                      >
                        <FontAwesomeIcon icon={faPaperPlane} size="md" />
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          ) : (
            <></>
          )}
        </div>
      ))}
      <div>
        <div className="h-5">
          {isLoading && (
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
          )}
        </div>
      </div>
    </div>
  );
}

export default Post;
