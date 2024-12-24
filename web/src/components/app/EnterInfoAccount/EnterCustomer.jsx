import React, { useState } from "react";
import { IoAddSharp } from "react-icons/io5";
import { CiUser } from "react-icons/ci";
import { BiLogoGmail } from "react-icons/bi";
import { FiPrinter } from "react-icons/fi";
import { FaMinus } from "react-icons/fa6";
import { MdPhone } from "react-icons/md"; 
import { ToastContainer, toast } from "react-toastify";
import { HiOutlineIdentification } from "react-icons/hi2";
import { useNavigate } from "react-router-dom";

const EnterCustomer = () => {
  
  const navigate = useNavigate();
  const [newEmployee, setNewEmployee] = useState({});

  const handleOnClick = () => {
    // console.log(!newEmployee);
    // if(!newEmployee) { 
    //   toast.error("Bạn cần nhập đầy đủ trường dữ liệu");
    //   return 
    // }
    navigate("/afterlogin")
  } 

  // const handleOnCancel = () => {
  //   navigate("/afterlogin")
  // }
  
  return (
    <>
      <div className="modal-add-emp">
          <div className="modal-add-content">
          <h3>Điền thông tin</h3>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <CiUser />
            <input
              type="text"
              placeholder="First name"
              className='tw-w-[90%]'
              value={newEmployee.name}
              onChange={(e) => setNewEmployee({ ...newEmployee, name: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <CiUser />
            <input
              type="text"
              placeholder="Last name"
              className='tw-w-[90%]'
              value={newEmployee.name}
              onChange={(e) => setNewEmployee({ ...newEmployee, name: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <MdPhone />
            <input
              type="text"
              placeholder="Phone Number"
              className='tw-w-[90%]'
              value={newEmployee.name}
              onChange={(e) => setNewEmployee({ ...newEmployee, name: e.target.value })}
            />
            </div>
            
            <div className='tw-flex tw-font-semibold tw-text-lg'> <BiLogoGmail />
            <input
              type="email"
              placeholder="Email"
              className='tw-w-[90%]'
              value={newEmployee.email}
              onChange={(e) => setNewEmployee({ ...newEmployee, email: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <HiOutlineIdentification />
            <input
              type="email"
              placeholder="MSSV( MSGV )"
              className='tw-w-[90%]'
              value={newEmployee.email}
              onChange={(e) => setNewEmployee({ ...newEmployee, email: e.target.value })}
            />
            </div>

            <div className="modal-buttons">
              <button onClick={() => handleOnClick()}>Confirm</button>
              <button onClick={() => handleOnClick()}>Cancel</button>
            </div>
          </div>
        </div>
        <ToastContainer /> 
    </>
  )
};

export default EnterCustomer;