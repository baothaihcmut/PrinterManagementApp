import React, { useState, useEffect } from "react";
import Nav from '../../layout/Nav';
import Footer from "../../layout/Footer";
import { FaRegUserCircle } from "react-icons/fa";
import { HiPrinter } from "react-icons/hi2";
import { LuNewspaper } from "react-icons/lu";
import { Link, useLocation, useParams } from "react-router-dom";
import { apiBaseUrl } from "../../../config";
import axios from "axios";


const InforPrinter = () => {
  // const location = useLocation();
  // const { printer } = location.state; // Get the selected printer from state

  // if (!printer) {
  //   return <p>Không có thông tin máy in.</p>; // Handle case where printer is not available
  // } // Get the selected printer from state
  const {id} = useParams();
  const [employees,setEmployees] = useState([]);

  const [transactions,setTransactions] = useState([]);
  
  const [printer,setPrinter] = useState({});

  useEffect(()=>{
    const fetchEmployees = async()=>{
      const api = `${apiBaseUrl}/printers/${id}/employees`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        const employees = res.data.data;
        setEmployees(employees.map(employee=>({
          id: employee.id.value,
          name: `${employee.firstName} ${employee.lastName}`,
          email: employee.email.value,
        })));
      } else {
        console.log(res.data.message);
      }
    }
    fetchEmployees();
  },[]);

  useEffect(()=>{
    const fetchPrinter = async()=>{
      const api = `${apiBaseUrl}/printers/${id}`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        const printer = res.data.data;
        setPrinter({
          ...printer,
        });
      }
    }
    fetchPrinter();
  },[]);

  useEffect(()=>{
    const fetchTransaction = async ()=>{
      const api = `${apiBaseUrl}/printers/${id}/transactions`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          sort: "desc:createdAt"
        }
      });
      if(res.status === 200) {
        setTransactions(res.data.data.data.map((transaction)=>({
          id: transaction.id.value,
          name: transaction.name,
          createdAt: transaction.createdAt,
        })));
      }
    }
    fetchTransaction();
  },[])

  return (
    <div>
      <Nav />
      <div className="tw-mt-24 tw-ml-52 tw-flex tw-justify-center tw-items-start tw-gap-8 tw-px-4 tw-py-6 w-full tw-font-roboto">
        {/* Column 1: Employee Information */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-green-200 tw-font-semibold tw-text-green-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Nhân viên
                <FaRegUserCircle className="tw-ml-2 tw-text-lg" />
              </h3>
              <ul className="tw-text-sm tw-text-black">
                {employees.map((employee) => (
                  <li key={employee.id} className="tw-mb-4 tw-flex tw-justify-between tw-text-black">
                    <Link to={`/account/employee/infor/${employee.id}`}><p className="tw-font-bold tw-ml-2 tw-cursor-pointer tw-text-black hover:tw-text-blue-500">{employee.name}</p></Link>
                    <p className="tw-text-black tw-mr-2">{employee.email}</p>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>

        {/* Column 2: Printer Information */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-orange-200 tw-font-semibold tw-text-orange-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Máy in
                <HiPrinter className="tw-ml-2 tw-text-lg" />
              </h3>
              <p className="tw-text-black">
                <span className="tw-font-bold">Tên máy:</span> {printer.name}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Trạng thái:</span>
                <span
                  className={`tw-inline-block tw-px-3 tw-py-1 tw-border tw-text-sm tw-font-semibold tw-rounded-full ${
                    printer.status === "ONLINE" ? "tw-bg-green-100 tw-text-green-600" : "tw-bg-red-100 tw-text-red-600"
                  }`}
                  
                >
                  {printer.status}
                </span>
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Location:</span> {printer.location}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Mã máy in:</span> {printer.code}
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
              <div className="tw-h-52 tw-overflow-y-auto">
                <ul className="tw-text-sm tw-text-gray-700">
                  {transactions.map((transaction, index) => (
                    <li key={index} className="tw-flex tw-justify-between">
                      <Link to={"/transaction-detail"}>
                        <p className="tw-font-bold tw-ml-2 tw-cursor-pointer tw-text-black hover:tw-text-blue-500">{transaction.name}</p>
                      </Link>
                      <p className="tw-text-black tw-mr-2">{new Date(transaction.createdAt).toLocaleString()}</p>
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

export default InforPrinter;
