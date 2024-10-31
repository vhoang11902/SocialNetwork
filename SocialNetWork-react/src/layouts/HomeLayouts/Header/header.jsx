import { NavLink, useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import logo from "../../../assets/logo192.png";
import { Popover } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import cookie from "react-cookies";
import {
  faMagnifyingGlass,
  faUsers,
  faHome,
  faBell,
  faMessage,
  faArrowRightFromBracket,
  faGear,
} from "@fortawesome/free-solid-svg-icons";
import MyContext from "../../../config/MyContext";
import NotiDrawer from "../../../components/notiDrawer";
import { NotisContext } from "../../../config/noti";
import RequestDrawer from "../../../components/friendRequestDrawer";
function Header() {
  const navigate = useNavigate();
  const [searchData, setSearchData] = useState("");
  const handleSearch = (e) => {
    e.preventDefault();
    navigate(`/search/${searchData}`);
  };
  const { notis } = useContext(NotisContext);

  const countNewNotis = () => {
    const newNotis = notis.filter((noti) => !noti.is_read);
    return newNotis.length;
  };

  const [user, dispatch] = useContext(MyContext);
    const logout = async () => {
      try {
          cookie.remove("token");
          dispatch({
              type: 'logout'
          });
          navigate('/login');
      } catch (ex) {
          console.error('Error logging out: ', ex);
      }
  };

  const commentOpt = (avatar) => (
    <div>
      <div className="w-[300px]">
        <NavLink
          to={`/profile/${user.id}`}
          className="pl-3 pr-6 py-2 flex items-center rounded-lg bg-white drop-shadow-xl"
        >
          <div className="rounded-full bg-red-500 mr-2">
            <img src={avatar} className="w-10 h-10 rounded-full" alt="Logo" />
          </div>
          <div className="text-lg font-semibold text-black">
            {user.lastName} {user.firstName}
          </div>
        </NavLink>
        <div className="my-4">
          <ul>
            <li className="text-base mb-2 px-2 py-2 hover:bg-gray-200 rounded-lg cursor-pointer transition-all flex items-center">
              <div className=" px-[6px] py-1 rounded-full bg-gray-200 mr-2">
                <FontAwesomeIcon icon={faGear} size="lg" />
              </div>
              <div className="">Cài đặt tài khoản</div>
            </li>
            <li className="text-base mb-2 px-2 py-2 hover:bg-gray-200 rounded-lg cursor-pointer transition-all flex items-center" onClick={logout}>
              <div className=" px-[6px] py-1 rounded-full bg-gray-200 mr-2">
                <FontAwesomeIcon icon={faArrowRightFromBracket} size="lg" />
              </div>
              <div className="">Đăng xuất</div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
  if(user)
    return (
        <div className="w-full sticky top-0 grid grid-cols-6 shadow-md bg-white">
          <div className="col-span-2 my-2 mx-4 flex justify-start">
            <NavLink to="/" className="flex mr-2 justify-center items-center">
              <img src={logo} className="w-10 h-10" alt="Logo" />
            </NavLink>
            <div className="">
              <form onSubmit={handleSearch} className="w-full relative">
                <div className="flex ">
                  <input
                    className="rounded-3xl h-8 w-full pl-10 p-5 bg-gray-200 focus:outline-none"
                    placeholder="Search"
                    name="keywords_submit"
                    type="text"
                    value={searchData}
                    onChange={(e) => setSearchData(e.target.value)}
                  />
                  <button
                    className="absolute z-20 left-0 h-[43px] w-[45px] "
                    type="submit"
                    name="search"
                    value="valueSearch"
                  >
                    <FontAwesomeIcon
                      icon={faMagnifyingGlass}
                      style={{ color: "#444444" }}
                      size="md"
                    />
                  </button>
                </div>
              </form>
            </div>
          </div>
  
          <div className="col-span-2 grid grid-flow-col">
            <div className="flex justify-center">
              <NavLink
                to="/home"
                className="flex w-36 mx-2 justify-center items-center hover:bg-slate-200 hover:rounded-md cursor-pointer"
              >
                <FontAwesomeIcon icon={faHome} size="xl" />
              </NavLink>
  
              <NavLink className="flex w-36 mx-2 justify-center items-center hover:bg-slate-200 hover:rounded-md cursor-pointer">
                <FontAwesomeIcon icon={faUsers} size="xl" />
              </NavLink>
            </div>
          </div>
  
          <div className="col-span-2 flex justify-end my-2 mx-4">
            {/* <div className="flex w-10 justify-center items-center rounded-full bg-gray-200 mx-1 cursor-pointer">
            <FontAwesomeIcon icon={faUser} size="lg" />
          </div> */}
            <Popover content={commentOpt(user.avatar)} trigger="click" overlayInnerStyle={{marginRight:"20px"}}>
              <div className="flex w-10 justify-center items-center rounded-full bg-gray-200 mx-1 cursor-pointer">
                <img
                  src={user.avatar}
                  className="w-10 h-10 rounded-full"
                  alt="Logo"
                />
              </div>
            </Popover>
            <Popover content={NotiDrawer} trigger="click" overlayInnerStyle={{marginRight:"20px"}}>
              <div className="flex w-10 justify-center items-center rounded-full bg-gray-200 mx-1 cursor-pointer relative">
                <FontAwesomeIcon icon={faBell} size="lg" />
                {countNewNotis() > 0 && (
                  <div className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-4 h-4 flex justify-center items-center">
                    {countNewNotis()}
                  </div>
                )}
              </div>
            </Popover>

            <Popover content={RequestDrawer} trigger="click" overlayInnerStyle={{marginRight:"20px"}}>
            <div className="flex w-10 justify-center items-center rounded-full bg-gray-200 mx-1 cursor-pointer">
              <FontAwesomeIcon icon={faMessage} size="lg" />
            </div>
            </Popover>

          </div>
        </div>

    );
  
  
}

export default Header;
