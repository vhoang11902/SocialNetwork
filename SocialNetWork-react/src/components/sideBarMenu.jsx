import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMagnifyingGlass,
  faCartShopping,
  faUser,
  faHome,
  faBell,
  faMessage,
  faMenu,
} from "@fortawesome/free-solid-svg-icons";
function SideBarMenu() {
  return (
    <div className="bg-blue-200 h-full text-left py-4 px-2">
      <div className="">
        <ul>
          <li className="flex bg-purple-300 p-2 my-2 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
            <div className="w-8 h-8 flex justify-center items-center rounded-full bg-red-400 mr-2">
              <FontAwesomeIcon icon={faHome} size="md" />
            </div>
            <div className="">Trinh Viet Hoang</div>
          </li>
          <li className="flex bg-purple-300 p-2 my-2 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
            <div className="w-8 h-8 flex justify-center items-center rounded-full bg-red-400 mr-2">
              <FontAwesomeIcon icon={faHome} size="md" />
            </div>
            <div className="">Trinh Viet Hoang</div>
          </li>
          <li className="flex bg-purple-300 p-2 my-2 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
            <div className="w-8 h-8 flex justify-center items-center rounded-full bg-red-400 mr-2">
              <FontAwesomeIcon icon={faHome} size="md" />
            </div>
            <div className="">Trinh Viet Hoang</div>
          </li>
          <li className="flex bg-purple-300 p-2 my-2 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
            <div className="w-8 h-8 flex justify-center items-center rounded-full bg-red-400 mr-2">
              <FontAwesomeIcon icon={faHome} size="md" />
            </div>
            <div className="">Trinh Viet Hoang</div>
          </li>
          <li className="flex bg-purple-300 p-2 my-2 items-center rounded-md hover:bg-gray-200 cursor-pointer transition-all">
            <div className="w-8 h-8 flex justify-center items-center rounded-full bg-red-400 mr-2">
              <FontAwesomeIcon icon={faHome} size="md" />
            </div>
            <div className="">Trinh Viet Hoang</div>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default SideBarMenu;
