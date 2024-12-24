import React, { useState } from "react";
import { IoAddSharp } from "react-icons/io5";
import { CiUser } from "react-icons/ci";
import { BiLogoGmail } from "react-icons/bi";
import { FiPrinter } from "react-icons/fi";
import { FaMinus } from "react-icons/fa6";
import { MdPhone } from "react-icons/md"; 
import { HiOutlineIdentification } from "react-icons/hi2";
import { useNavigate } from "react-router-dom";

const EnterEmployee = () => {
  const [newEmployee, setNewEmployee] = useState({});
  const navigate = useNavigate();

  const handleOnClick = () => 
  {
    navigate("/afterlogin")
  }
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

            <div className="modal-buttons">
              <button onClick={() => handleOnClick()}>Confirm</button>
              <button onClick={() => handleOnClick()}>Cancel</button>
            </div>
          </div>
        </div>
    </>
  )
};

export default EnterEmployee;