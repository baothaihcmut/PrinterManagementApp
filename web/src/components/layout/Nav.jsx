import React , { useRef, useState } from "react";
import { NavLink, Link, Outlet } from "react-router-dom";
import PrinterImg from '../../assets/images/logo-new.png';
import { IoIosMenu } from "react-icons/io";
import Sidebar from "./Sidebar";

const Nav = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [hiddenSidebar, setHiddenSidebar] = useState(false);

  const getWidth = useRef(null);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const toggleSidebar = () => {
    setHiddenSidebar(!hiddenSidebar)
  }
  
  return (
    <>
    
      <nav ref={getWidth} className="tw-fixed tw-top-0 tw-left-0 tw-z-50 tw-w-full tw-flex tw-items-center tw-justify-between tw-bg-[aliceblue] tw-py-4 tw-px-6 tw-border-b tw-border-gray-200">
      {/* Left Section */}
      <div className="tw-flex tw-items-center">
        <button className="tw-inline-block tw-p tw-mr-6"
          onClick={toggleSidebar}>
          <IoIosMenu />
        </button>

        {/* Logo */}
        <img className="tw-w-11 tw-h-auto" src={PrinterImg} alt="printer" />
      </div>

      {/* Right Section */}
      <div className="tw-flex tw-items-center tw-space-x-4">
        {/* Status */}
        <span className="tw-bg-green-100 tw-text-green-600 tw-px-3 tw-py-1 tw-rounded-full tw-text-sm">
          Online
        </span>
        {/* Profile */}
        <div className="tw-relative">
            <img
              src="https://via.placeholder.com/40"
              alt="Profile"
              className="tw-h-10 tw-w-10 tw-rounded-full tw-cursor-pointer"
              onClick={toggleDropdown}
            />
            {dropdownOpen && (
              <div className="tw-absolute tw-right-0 tw-mt-2 tw-w-48 tw-bg-white tw-rounded-lg tw-shadow-lg tw-border tw-border-gray-200">
                <ul className="tw-py-2">
                  <li>
                    <Link 
                      to="/account/infor"
                      className="tw-block tw-px-4 tw-py-2 tw-text-gray-700 hover:tw-bg-gray-100">
                        Profile
                    </Link>
                  </li>
                  <li>
                    <Link 
                      to=""
                      className="tw-block tw-px-4 tw-py-2 tw-text-gray-700 hover:tw-bg-gray-100">
                        Setting
                    </Link>
                  </li>
                  <li>
                    <Link 
                      to="/"
                      className="tw-block tw-px-4 tw-py-2 tw-text-gray-700 hover:tw-bg-gray-100">
                      Log out
                    </Link>
                  </li>
                </ul>
              </div>
            )}
          </div>
      <Outlet />
      </div>
    </nav>
    
    {hiddenSidebar && <Sidebar />}
  </>

)};

export default Nav;