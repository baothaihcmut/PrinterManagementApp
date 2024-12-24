import { useEffect, useState } from "react"; // Make sure to import useState
import Nav from '../../layout/Nav';
import Footer from "../../layout/Footer";
import { FaRegUserCircle } from "react-icons/fa";
import { LuNewspaper } from "react-icons/lu";
import { Link, useParams } from "react-router-dom";
import { IoDocumentsOutline } from "react-icons/io5";
import { apiBaseUrl } from "../../../config";
import axios from "axios";

const CusAccount = () => {
  const {id} = useParams();
  const [customer,setCustomer] = useState({});
  const [transactions,setTransactions] = useState([]);
  const [documents,setDocuments] = useState([]);

  useEffect(()=>{
    const fetchCustomer = async()=>{
      const api = `${apiBaseUrl}/users/${id}`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if(res.status === 200) {
        const customerRes = res.data.data;
        setCustomer({
          name: `${customerRes.name.firstName} ${customerRes.name.lastName}`,
          email : customerRes.email.value,
          phoneNumber: customerRes.phoneNumber,
          isActive: customerRes.isActive,
          role: customerRes.role,
        });
      }
    }
    fetchCustomer();
  },[]);
  useEffect(()=>{
    const fetchTransaction = async ()=>{
      const api = `${apiBaseUrl}/transactions/customer/${id}`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}` 
        },
        params: {
          sort: 'desc:createdAt'
        },
      });
      if (res.status === 200) {
        setTransactions(res.data.data.data.map((transaction)=>({
          id: transaction.id.value,
          name: transaction.name,
          createdAt: new Date(transaction.createdAt).toLocaleDateString(),
        })))
      }
    };
    fetchTransaction();
  },[]);
  useEffect(()=>{
    const fetchDocument = async()=>{
      const api = `${apiBaseUrl}/customers/${id}/documents`;
      const token = localStorage.getItem("accessTokenAdmin");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`, 
        }
      });
      if(res.status ===200) {
        setDocuments(res.data.data.map((doc)=>({
          id: doc.id.value,
          name: doc.name,
          link: doc.link
        })))
      }
    };
    fetchDocument();
  },[])
  

  
  const handleDownload = async (fileUrl, name) => {
    try {
      const response = await axios.get(fileUrl, {
        responseType: "blob", // Ensure the response is treated as a binary file
      });

      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", name); // Specify the filename
      document.body.appendChild(link);
      link.click();
      link.remove(); // Clean up
    } catch (error) {
      console.error("File download failed:", error);
    }
  };

  // Dữ liệu giả định cho thông tin khách hàng và các tài liệu


  return (
    <div>
      <Nav />
      <div className="tw-mt-24 tw-ml-52 tw-flex tw-justify-center tw-items-start tw-gap-8 tw-px-4 tw-py-6 w-full tw-font-roboto">
        {/* Column 1: Recent Transactions */}
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

        {/* Column 2: User Information */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-orange-200 tw-font-semibold tw-text-orange-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Thông tin người dùng
                <FaRegUserCircle className="tw-ml-2 tw-text-lg" />
              </h3>
              <p className="tw-text-black">
                <span className="tw-font-bold">Tên:</span> {customer.name}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Email:</span> {customer.email}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Số điện thoại:</span> {customer.phoneNumber}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Trạng thái:</span> {customer.isActive? 'Đã kích hoạt':'Chưa kích hoạt'}
              </p>
            </div>
          </div>
        </div>

        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg tw-text-center">
        <div className="tw-info-card tw-mb-6">
          <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
            <h3 className="tw-text-sm tw-font-semibold tw-py-2 tw-bg-red-200 tw-text-red-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
              Tài liệu
              <IoDocumentsOutline className="tw-ml-2 tw-text-lg" />
            </h3>
            <div className="tw-h-48 tw-overflow-y-auto">
            <ul className="tw-text-s tw-text-gray-700 ">
              {documents.map((doc, index) => (
                <li
                  key={index}
                  className="tw-mb-6"
                >
                  <span
                    className={`tw-document-item tw-cursor-pointer tw-p-2 tw-text-black hover:tw-text-blue-500`}
                  >
                    {doc.name}
                  </span>
                  <button
                    className="tw-bg-blue-500 tw-text-white tw-ml-1 px-2 py-0 text-sm tw-rounded-full hover:bg-blue-600"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleDownload(doc.link,doc.name);
                    }}
                  >
                    ⤓
                  </button>
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

export default CusAccount;