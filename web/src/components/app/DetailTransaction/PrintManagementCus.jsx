import  { useState, useEffect } from "react";
import Nav from "../../layout/Nav";
import Footer from "../../layout/Footer";
import { FaRegUserCircle } from "react-icons/fa";
import { HiPrinter } from "react-icons/hi2";
import { LuNewspaper } from "react-icons/lu";
import { HiOutlineInformationCircle } from "react-icons/hi";
import { IoDocumentsOutline } from "react-icons/io5";
import axios from "axios";
import { apiBaseUrl } from "../../../config";
import { useParams } from "react-router-dom";

const PrintManagement = () => {
  const {id} = useParams();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedDocument, setSelectedDocument] = useState(null);
  const [status, setStatus] = useState("unconfirmed");
  const [timelinePosition, setTimelinePosition] = useState(0);
  const [timestamps, setTimestamps] = useState({});


  const [employee,setEmployee] = useState({});
  const [printerInfo,setPrinterInfo] = useState({});
  const [transaction,setTransaction] = useState({});
  const [documents,setDocuments] = useState([]);


  useEffect(()=>{
    const fetchTransaction = async()=>{
      const api = `${apiBaseUrl}/transactions/${id}`;
      const token = localStorage.getItem("accessTokenCustomer");
      const res = await axios.get(api,{
        headers:{
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        const transactionRes = res.data.data;
        setTransaction({
          id: transactionRes.id.value,
          name: transactionRes.name,
          transactionId: transactionRes.transactionId,
          totalA3: transactionRes.transactionPaperQuantities.A3?.quantity || 0,
          totalA4: transactionRes.transactionPaperQuantities.A4?.quantity || 0,
          totalA5: transactionRes.transactionPaperQuantities.A5?.quantity || 0,
        });
        setStatus(transactionRes.status);
        setTimestamps({
          createdAt: transactionRes.createdAt ? new Date(transactionRes.createdAt).toLocaleString():null,
          acceptedAt: transactionRes.acceptedAt? new Date(transactionRes.acceptedAt).toLocaleString():null,
          doneAt:transactionRes.doneAt?new Date(transactionRes.doneAt).toLocaleString():null,
        });
        setDocuments(transactionRes.transactionDocuments.map((doc,index)=>({
          id: doc.id,
          name: doc.name,
          ...doc.documentDetail,
          link: transactionRes.urls[index],
        })));
        if(transactionRes.status === "PENDING") setTimelinePosition(0);
        else if(transactionRes.status === 'PROCESS') setTimelinePosition(1);
        else if(transactionRes.status === 'DONE') setTimelinePosition(2);
      } else {
        console.log(res.data.message);
      }
    }
    fetchTransaction();
  },[]);
  useEffect(()=>{
    const fetchEmployee = async ()=>{
      const api = `${apiBaseUrl}/transactions/${id}/employee`;
      const token = localStorage.getItem("accessTokenCustomer");
      const res = await axios.get(api,{
        headers:{
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        const customerRes = res.data.data;
        setEmployee({
          id: customerRes.id.value,
          name: `${customerRes.name.firstName} ${customerRes.name.lastName}`,
          email: customerRes.email.value,
          phoneNumber: customerRes.phoneNumber,
        })
      } else{
        console.log(res.data.message);
      }
    };
    fetchEmployee();
  },[]);

  useEffect(()=>{
    const fetchPrinter = async()=>{
      const api = `${apiBaseUrl}/transactions/${id}/printer`;
      const token = localStorage.getItem("accessTokenCustomer");
      const res = await axios.get(api,{
        headers:{
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        const printerRes = res.data.data;
        setPrinterInfo({
          id: printerRes.id.value,
          name: printerRes.name,
          code: printerRes.code,
          status: printerRes.status,
          location: printerRes.location,
        });
      }
    }
    fetchPrinter();
  },[])

  const handleDocumentClick = (document) => {
    setSelectedDocument(document);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedDocument(null);
  };
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


  return (
    <div>
      <Nav />
      <div className="tw-mt-20 tw-main-content tw-flex tw-justify-center tw-items-start tw-gap-8 tw-px-4 tw-py-6 w-full">
        {/* Column 1: Transaction Info */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg">
          {/* Transaction Name */}
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-blue-200 tw-font-semibold tw-text-blue-600 tw-mb-4 tw-flex tw-items-center tw-justify-center ">
                {transaction.name}
                <LuNewspaper className="tw-ml-2 tw-text-lg" />
              </h3>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Giao dịch </span>#{transaction.transactionId}
              </p>
            </div>
          </div>

          {/* User Info */}
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-green-200 tw-font-semibold tw-text-green-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Nhân viên
                <FaRegUserCircle className="tw-ml-2 tw-text-lg" />{" "}
                {/* Icon with margin-right */}
              </h3>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Tên:</span> {employee.name}
              </p>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Email:</span> {employee.email}
              </p>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Số điện thoại:</span> {employee.phoneNumber}
              </p>
            </div>
          </div>

          {/* Printer Info */}
          <div className="tw-info-card tw-mb-6">
            <div className="tw-text-center tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
              <h3 className="tw-text-sm tw-py-2 tw-bg-orange-200 tw-font-semibold tw-text-orange-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
                Máy in
                <HiPrinter className="tw-ml-2 tw-text-lg" />{" "}
                {/* Icon on the right */}
              </h3>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Tên máy:</span> {printerInfo.name}
              </p>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Trạng thái:<span
                        className={`tw-inline-block tw-px-3 tw-py-1 tw-border tw-text-sm tw-font-semibold tw-rounded-full ${
                          printerInfo.status === "ONLINE"
                            ? "tw-bg-green-100 tw-text-green-600"
                            : "tw-bg-red-100 tw-text-red-600"
                        }`}
                      >
                        {printerInfo.status}
                      </span>
                      </span>
                      </p>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Location:</span> {printerInfo.location}
              </p>
              <p className="tw-text-sm tw-text-gray-700">
                <span className="tw-font-bold">Mã máy in:</span> {printerInfo.code}
              </p>
            </div>
          </div>
        </div>

        {/* Column 2: Transaction Details */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg tw-text-center">
          <div className="tw-info-card tw-mb-6 tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
            <h3 className="tw-text-sm tw-font-semibold tw-py-2 tw-bg-blue-200 tw-text-blue-600 tw-text-center tw-mb-4 tw-flex tw-items-center tw-justify-center">
            Thông tin Giao dịch
      <HiOutlineInformationCircle className="tw-ml-2 tw-text-lg" />
    </h3>
    
    {/* Số lượng giấy sử dụng */}
    <p className="tw-text-sm tw-text-gray-700">
      <span className="tw-font-bold">Số lượng giấy sử dụng:</span>
    </p>
    
    {/* Danh sách loại giấy đã sử dụng */}
    <ul className="tw-text-sm tw-text-gray-700 tw-mb-4">
      <li>
        <span className="tw-font-bold">A3:</span> {transaction.totalA3} tờ
      </li>
      <li>
        <span className="tw-font-bold">A4:</span> {transaction.totalA4} tờ
      </li>
      <li>
        <span className="tw-font-bold">A5:</span> {transaction.totalA5} tờ
      </li>
    </ul>
            <p className="tw-text-sm tw-text-gray-700">
              <span className="tw-font-bold"> Trạng thái: </span> 

              <span className="tw-inline-block tw-px-3 tw-py-1 tw-border tw-text-sm tw-font-semibold tw-rounded-full tw-bg-green-400"> {status}</span>
            </p>
            
            {/* Timeline Container */}
            <div
              className="tw-relative tw-h-2 tw-bg-gray-400 tw-rounded-full tw-mt-4"
              style={{ width: "80%", marginLeft: "auto", marginRight: "auto" }}
            >
              {/* Timeline Progress Bar */}
              <div
                className="tw-absolute tw-h-2 tw-rounded-full tw-bg-green-500"
                style={{
                  top: "50%",
                  transform: "translateY(-50%)",
                  width: `${
                    timelinePosition === 0
                      ? 0
                      : timelinePosition === 1
                      ? 50
                      : 100
                  }%`,
                }}
              ></div>

              {/* Timeline Dots */}
              <div
                className={`tw-absolute tw-w-4 tw-h-4 tw-rounded-full ${
                  timelinePosition >= 0 ? "tw-bg-blue-500" : "tw-bg-gray-400"
                }`}
                style={{
                  top: "50%",
                  transform: "translateY(-50%)",
                  left: "0%",
                }}
              ></div>
              <div
                className={`tw-absolute tw-w-4 tw-h-4 tw-rounded-full ${
                  timelinePosition >= 1 ? "tw-bg-blue-500" : "tw-bg-gray-400"
                }`}
                style={{
                  top: "50%",
                  transform: "translateY(-50%)",
                  left: "50%",
                }}
              ></div>
              <div
                className={`tw-absolute tw-w-4 tw-h-4 tw-rounded-full ${
                  timelinePosition === 2 ? "tw-bg-blue-500" : "tw-bg-gray-400"
                }`}
                style={{
                  top: "50%",
                  transform: "translateY(-50%)",
                  right: "0%",
                }}
              ></div>
            </div>

            {/* Timeline Labels */}
            <div className="tw-flex tw-justify-between tw-text-sm tw-font-semibold tw-mt-2">
              <div className="tw-text-center" style={{ width: "33.33%" }}>
                <p
                  className={`tw-mb-1 ${
                    timelinePosition === 0
                      ? "tw-text-red-500 tw-font-bold"
                      : "tw-text-gray-500"
                  }`}
                >
                  Created
                </p>
                {timestamps.createdAt && (
                  <>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 0
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.createdAt.split(",")[0]}
                    </p>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 0
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.createdAt.split(",")[1]}
                    </p>
                  </>
                )}
              </div>
              <div className="tw-text-center" style={{ width: "33.33%" }}>
                <p
                  className={`tw-mb-1 ${
                    timelinePosition === 1
                      ? "tw-text-green-500 tw-font-bold"
                      : "tw-text-gray-500"
                  }`}
                >
                  Accept
                </p>
                {timestamps.acceptedAt && (
                  <>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 1
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.acceptedAt.split(",")[0]}
                    </p>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 1
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.acceptedAt.split(",")[1]}
                    </p>
                  </>
                )}
              </div>
              <div className="tw-text-center" style={{ width: "33.33%" }}>
                <p
                  className={`tw-mb-1 ${
                    timelinePosition === 2
                      ? "tw-text-blue-500 tw-font-bold"
                      : "tw-text-gray-500"
                  }`}
                >
                  Done
                </p>
                {timestamps.doneAt && (
                  <>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 2
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.doneAt.split(",")[0]}
                    </p>
                    <p
                      className={`tw-text-black ${
                        timelinePosition === 2
                          ? "tw-text-black "
                          : "tw-text-gray-500"
                      }`}
                    >
                      {timestamps.doneAt.split(",")[1]}
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>

         
        </div>

        {/* Column 3: Document Info */}
        <div className="tw-w-1/3 tw-p-6 tw-bg-white tw-shadow-xl tw-rounded-lg tw-text-center">
          <div className="tw-info-card tw-overflow-hidden tw-border tw-border-gray-300 tw-rounded-lg">
            <h3 className="tw-text-sm tw-font-semibold tw-py-2 tw-bg-red-200 tw-text-red-600 tw-mb-4 tw-flex tw-items-center tw-justify-center">
              Tài liệu
              <IoDocumentsOutline className="tw-ml-2 tw-text-lg" />
            </h3>
            <ul className="tw-text-sm tw-text-gray-700">
              {documents.map((doc, index) => (
                <li
                  key={index}
                  onClick={() => handleDocumentClick(doc)}
                  className="tw-mb-6" // Thêm khoảng cách giữa các tài liệu
                >
                  <span
                    className={`tw-document-item tw-cursor-pointer tw-p-2 tw-text-black hover:tw-text-blue-500`}
                  >
                    {doc.name} - {doc.paperType} - {doc.numOfCopies} Bản
                    </span>
                  <button
                    className="tw-bg-blue-500 tw-text-white tw-ml-1 px-2 py-0 text-sm tw-rounded-full hover:bg-blue-600"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleDownload(doc);
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

      {/* Modal for Document */}
      {isModalOpen && selectedDocument && (
        <div className="tw-modal tw-fixed tw-inset-0 tw-bg-gray-800 tw-bg-opacity-50 tw-flex tw-items-center tw-justify-center tw-z-50">
          <div
            className="tw-modal-content tw-bg-white tw-p-6 tw-rounded-xl tw-shadow-2xl tw-z-50"
            style={{
              transform: "scale(1)",
              transformOrigin: "center",
              width: "auto", // Điều chỉnh để modal có kích thước tự động
              maxWidth: "500px", // Giới hạn kích thước tối đa
              padding: "16px", // Điều chỉnh padding để thu gọn nội dung
            }}
          >
            <div className="tw-relative">
              {/* Close Button */}
              <button
                onClick={closeModal}
                className="tw-absolute tw-top-0 tw-right-0 tw-mt-2 tw-mr-2 tw-text-2xl tw-text-gray-700 hover:tw-text-gray-900"
              >
                &times;
              </button>

              <div className="tw-modal-body">
                <h3 className="tw-text-xl tw-font-semibold tw-bg-blue-200 tw-text-blue-600 tw-py-2 tw-rounded tw-mt-4 tw-mb-6 tw-text-center">
                  Thông tin Tài liệu
                </h3>

                {/* File Information (Split into two columns) */}
                <div className="tw-flex tw-gap-8">
                  {/* Left Column (Cột trái) */}
                  <div className="tw-w-1/2">
                    <p className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        Tên tài liệu:
                      </span>{" "}
                      <span className="tw-text-black">
                        {selectedDocument.name}
                      </span>
                    </p>
                    <p className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        Số bản:
                      </span>{" "}
                      <span className="tw-text-black">
                        {selectedDocument.numOfCopies}
                      </span>
                    </p>
                    <p className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        In 1 mặt:
                      </span>{" "}
                      <span className="tw-text-black">
                      {selectedDocument.isOneSide ? 'Có': 'Không'}
                      </span>
                    </p>
                  </div>

                  {/* Right Column (Cột phải) */}
                  <div className="tw-w-1/2 tw-text-left">
                    <p className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        Khổ giấy:
                      </span>{" "}
                      <span className="tw-text-black">A4</span>
                    </p>
                    <p className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        Hướng in:
                      </span>{" "}
                      <span className="tw-text-black">Dọc</span>
                    </p>
                    <div className="tw-mb-4">
                      <span className="tw-font-bold tw-text-black">
                        Căn lề:
                      </span>
                      <div className="tw-text-black">
                        <p className="tw-inline-block tw-mr-6 tw-text-black">
                          Top: {selectedDocument.topSide}mm
                        </p>
                        <p className="tw-inline-block tw-text-black">
                          Bottom: {selectedDocument.bottomSide}mm
                        </p>
                      </div>
                      <div className="tw-text-black">
                        <p className="tw-inline-block tw-mr-6 tw-text-black">
                          Left: {selectedDocument.leftSide}mm
                        </p>
                        <p className="tw-inline-block tw-text-black">
                          Right: {selectedDocument.rightSide}mm
                        </p>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Save Option */}
                <div className="tw-flex tw-flex-col tw-items-center">
                  <p className="tw-ml-6 tw-mr-6">
                    <span className="tw-font-bold tw-text-black">
                      Lưu trong 7 ngày:
                    </span>{" "}
                    <span className="tw-text-black">Có</span>
                  </p>
                </div>

                {/* Download Button */}
                <div className="tw-mt-6 tw-flex tw-justify-center">
                  <button
                    className="tw-bg-blue-500 tw-text-white tw-px-6 tw-py-2 tw-rounded-full hover:tw-bg-blue-700 tw-z-10"

                    onClick={() =>  handleDownload(selectedDocument.link,selectedDocument.name)}
                  >
                    Tải tài liệu
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
};

export default PrintManagement;