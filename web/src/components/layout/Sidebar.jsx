
import { Link } from "react-router-dom";
import { MdOutlineAccountCircle, MdOutlineManageAccounts } from "react-icons/md";
import { IoCloudUploadOutline } from "react-icons/io5";
import { FaCartShopping } from "react-icons/fa6";
import { MdManageHistory } from "react-icons/md";
import { LuPrinterCheck } from "react-icons/lu";


const role = [
  "customer",
  "admin",
  "employee"
]

const manageTrans = (roleAcc) => {
  if(roleAcc === 'employee') {
    return(
      <Link to={`/employee/printers`} className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
    <MdManageHistory className="tw-w-5 tw-h-5"/>
    <span className="tw-ms-3">Transaction Management</span>
  </Link>
    )
  }
  return (
  <Link to={`/${role[role.indexOf(roleAcc)]}/management/transaction`} className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
    <MdManageHistory className="tw-w-5 tw-h-5"/>
    <span className="tw-ms-3">Transaction Management</span>
  </Link>
)}

const managePrinter = (roleAcc) => {
  if(roleAcc === role[0]) return;
  return (
    <Link to={`/admin/printers`} className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
    <LuPrinterCheck />
    <span className="tw-ms-3">Printer Management</span>
  </Link>
)}


const manageUser = (roleAcc) => {
  return roleAcc === role[1] && (
    <Link to={`/${role[role.indexOf(roleAcc)]}/management/user`} className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
    <MdOutlineManageAccounts className="tw-w-5 tw-h-5"/>
    <span className="tw-ms-3">User Management</span>
  </Link>
)}

const Sidebar = () => {
  const roleAcc = localStorage.getItem("role");
  

  return (
    <aside className="tw-fixed tw-rounded-md tw-top-[73px] tw-left-0 tw-z-40 tw-max-h-min tw-w-60 tw-transition-transform sm:tw-translate-x-0  tw-bg-white tw-border-r tw-border-gray-200">
      <div className="tw-h-full">
        <ul className="tw-space-y-2 tw-font-medium">
          <li >
            <Link to='/account/infor' className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 hover:tw-text-customBlue hover:tw-bg-gray-100">
              <MdOutlineAccountCircle className="tw-w-5 tw-h-5" />
              <span className="tw-ms-3">Account</span>
            </Link>
          </li>
          {
          roleAcc === role[0] && 
          <>
            <li >
              <Link to='/upload' className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
              <IoCloudUploadOutline className="tw-w-5 tw-h-5"/>
                <span className="tw-ms-3">Upload</span>
              </Link>
            </li> 
            
            <li >
              <Link to='/paper/choose' className="tw-flex tw-items-center tw-p-2 tw-text-gray-900 tw-rounded-lg hover:tw-text-customBlue hover:tw-bg-gray-100">
              <FaCartShopping className="tw-w-5 tw-h-5"/>
                <span className="tw-ms-3">Buy Paper</span>
              </Link>
            </li> 
            <li >
              {manageTrans(roleAcc)}
            </li>
          </>
          }

          {
            roleAcc === role[2] && 
            <li>
              {manageTrans(roleAcc)}
            </li>
          }

          {
            roleAcc === role[1] && 
            <>
              <li>{managePrinter(roleAcc)}</li>
              <li>{manageTrans(roleAcc)}</li>
              <li>{manageUser(roleAcc)}</li>
            </> 
          }

          
        </ul>
      </div>
    </aside>
  );
};

export default Sidebar;