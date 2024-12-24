import  { useEffect, useState } from "react";
import Nav from '../../layout/Nav';
import Footer from "../../layout/Footer";
import { FaRegUserCircle } from "react-icons/fa";
import { HiPrinter } from "react-icons/hi2";
import { LuNewspaper } from "react-icons/lu";
import { Link, useParams } from "react-router-dom";
import { apiBaseUrl } from "../../../config";
import axios from "axios";

const EmployAccount = () => {
  const {id} = useParams();
  // Dữ liệu giả định cho thông tin nhân viên và các máy in
  const [employee,setEmployee] = useState({});
  const [transactions,setTransactions] = useState([]);
  const [printers,setPrinters] = useState([]);
  useEffect(()=>{
    const fetchEmployee = async()=>{
      const api = `${apiBaseUrl}/users/${id}`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      if(res.status === 200) {
        const employeeRes = res.data.data;
        setEmployee({
          name: `${employeeRes.name.firstName} ${employeeRes.name.lastName}`,
          email : employeeRes.email.value,
          phoneNumber: employeeRes.phoneNumber,
          isActive: employeeRes.isActive,
          role: employeeRes.role,
          employeeId: employeeRes.employee.employeeId,
          startWorkDate: new Date(employeeRes.employee.startWorkDate).toLocaleDateString(),
        });
      }
    }
    fetchEmployee();
  },[]);
  useEffect(()=>{
    const fetchTransction = async()=>{
      const api = `${apiBaseUrl}/transactions/employee/${id}`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          sort: "desc:createdAt",
        },
      });
      if(res.status===200) {
        const transactionsRes = res.data.data.data; 
        setTransactions(transactionsRes.map((transaction)=>({
          id: transaction.id.value,
          name: transaction.name,
          createdAt: new Date(transaction.createdAt).toLocaleDateString(),
        })))
      }
    }
    fetchTransction();
  },[]);
  useEffect(()=>{
    const fetchPrinter = async ()=>{
      const api = `${apiBaseUrl}/employees/${id}/printers`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status===200) {
        const printers = res.data.data;
        setPrinters(printers.map((printer=>({
          id: printer.id.value,
          name: printer.name,
        }))));
      }
    }
    fetchPrinter();
  },[]);
  
 
  return (
    <div>
      <Nav />
      <div className="tw-mt-24 tw-ml-52 tw-flex tw-justify-center tw-items-start tw-gap-8 tw-px-4 tw-py-6 w-full tw-font-roboto">
        
        {/* Column 1: Printer Information */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-green-200 tw-font-semibold tw-text-green-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Máy in
                <HiPrinter className="tw-ml-2 tw-text-lg" />
              </h3>
              <div className="tw-h-48 tw-overflow-y-auto"> {/* Thêm chiều cao cố định và thanh cuộn */}
                <ul className="tw-text-sm tw-text-black tw-text-center">
                  {printers.map((printer) => (
                    <li key={printer.id} className="tw-mb-4 tw-flex tw-justify-center tw-text-black">
                       <Link to={`/printer/infor/${printer.id}`}>
                        <p className="tw-font-bold tw-ml-2 tw-cursor-pointer tw-text-black hover:tw-text-blue-500">
                          {printer.name}
                        </p>
                      </Link>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        </div>

        {/* Column 2: Employee Information */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-orange-200 tw-font-semibold tw-text-orange-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Thông tin nhân viên
                <FaRegUserCircle className="tw-ml-2 tw-text-lg" />
              </h3>
              <p className="tw-text-black">
                <span className="tw-font-bold">Tên:</span> {employee.name}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Mã nhân viên:</span> {employee.employeeId}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Email:</span> {employee.email}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Số điện thoại:</span> {employee.phoneNumber}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Ngày bắt đầu làm việc:</span> {employee.startWorkDate}
              </p>
            </div>
          </div>
        </div>

        {/* Column 3: Recent Transactions */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-blue-200 tw-font-semibold tw-text-blue-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Giao dịch gần đây
                <LuNewspaper className="tw-ml-2 tw-text-lg" />
              </h3>
              <div className="tw-h-48 tw-overflow-y-auto">
                <ul className="tw-text-sm tw-text-gray-700">
                  {transactions.map((transaction) => (
                    <li key={transaction.id} className="tw-flex tw-justify-between">
                      <Link to={`/admin/print/detail/${transaction.id}`}>
                        <p className="tw-font-bold tw-ml-2 tw-cursor-pointer tw-text-black hover:tw-text-blue-500">{transaction.name}</p>
                      </Link>
                      <p className="tw-text-black tw-mr-2">{transaction.createdAt}</p>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default EmployAccount;
