import React, { useState } from "react"; // Make sure to import useState
import Nav from '../../layout/Nav';
import Footer from "../../layout/Footer";
import { FaRegUserCircle } from "react-icons/fa";
import { LuNewspaper } from "react-icons/lu";
import { Link } from "react-router-dom";
import { IoDocumentsOutline } from "react-icons/io5";

const CustomerInfo = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedDocument, setSelectedDocument] = useState(null);

  const handleDocumentClick = (document) => {
    setSelectedDocument(document);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedDocument(null);
  };

  const handleDownload = (document) => {
    const fileUrl = `/path/to/your/files/${document.name}`;

    const link = document.createElement("a");
    link.href = fileUrl;
    link.download = document.name;
    link.click();
  };

  // Dữ liệu giả định cho thông tin khách hàng và các tài liệu
  const customer = {
    name: "Thái Bảo",
    phone: "0123456789", // Số điện thoại
    firstLoginDate: new Date(2023, 5, 15), // Ngày đầu tiên đăng nhập vào hệ thống
    transactions: [
      { id: 1, name: "Giao dịch 1", timestamp: new Date() },
      { id: 2, name: "Giao dịch 2", timestamp: new Date(Date.now() - 1000 * 60 * 60 * 24) },
      { id: 3, name: "Giao dịch 3", timestamp: new Date(Date.now() - 1000 * 60 * 60 * 48) },
      { id: 4, name: "Giao dịch 4", timestamp: new Date(Date.now() - 1000 * 60 * 60 * 72) },
      { id: 5, name: "Giao dịch 5", timestamp: new Date(Date.now() - 1000 * 60 * 60 * 96) },
    ],
    documents: [
      { id: 1, name: "Tài liệu A.pdf", size: "2MB" },
      { id: 2, name: "Tài liệu B.docx", size: "1.5MB" },
      { id: 3, name: "Tài liệu C.xlsx", size: "3MB" },
      { id: 1, name: "Tài liệu A.pdf", size: "2MB" },
      { id: 2, name: "Tài liệu B.docx", size: "1.5MB" },
      { id: 3, name: "Tài liệu C.xlsx", size: "3MB" },
      { id: 1, name: "Tài liệu A.pdf", size: "2MB" },
      { id: 2, name: "Tài liệu B.docx", size: "1.5MB" },
      { id: 3, name: "Tài liệu C.xlsx", size: "3MB" },
    ],
    
    
  };

  return (
    <div>
      <Nav />
      <div className="tw-mt-20 tw-main-content tw-flex tw-justify-center tw-items-start tw-gap-8 tw-px-4 tw-py-6 w-full tw-font-roboto">
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
                  {customer.transactions.map((transaction, index) => (
                    <li key={index} className="tw-flex tw-justify-between">
                      <Link to={"/transaction-detail"}>
                        <p className="tw-font-bold tw-ml-2 tw-cursor-pointer tw-text-black hover:tw-text-blue-500">{transaction.name}</p>
                      </Link>
                      <p className="tw-text-black tw-mr-2">{new Date(transaction.timestamp).toLocaleString()}</p>
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
                <span className="tw-font-bold">Số điện thoại:</span> {customer.phone}
              </p>
              <p className="tw-text-black">
                <span className="tw-font-bold">Ngày đăng nhập lần đầu:</span> {new Date(customer.firstLoginDate).toLocaleDateString()}
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
              {customer.documents.map((doc, index) => (
                <li
                  key={index}
                  onClick={() => handleDocumentClick(doc)}
                  className="tw-mb-6"
                >
                  <span
                    className={`tw-document-item tw-cursor-pointer tw-p-2 tw-text-black hover:tw-text-blue-500`}
                  >
                    {doc.name} - {doc.size} - {doc.pages} trang
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
        </div>
        </div>

      {/* Modal for Document */}
{isModalOpen && selectedDocument && (
  <div className="tw-modal tw-fixed tw-inset-0 tw-bg-gray-800 tw-bg-opacity-50 tw-flex tw-items-center tw-justify-center tw-z-50">
    <div className="tw-modal-content tw-bg-white tw-p-6 tw-rounded-xl tw-shadow-2xl tw-max-w-2xl tw-w-full tw-z-50">
      <div className="tw-relative">
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

          <div className="tw-flex tw-gap-8 tw-justify-center ">
            {/* Left Column */}
            <div className="tw-w-full sm:tw-w-1/2 tw-px-4">
              <p className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Tên tài liệu:</span>{" "}
                <span className="tw-text-black">{selectedDocument.name}</span>
              </p>
              <p className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Kích thước:</span>{" "}
                <span className="tw-text-black">{selectedDocument.size}</span>
              </p>
              <p className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Số lượng trang:</span>{" "}
                <span className="tw-text-black">{selectedDocument.pages}</span>
              </p>
            </div>

            {/* Right Column */}
            <div className="tw-w-full sm:tw-w-1/2 tw-px-4 tw-text-left">
              <p className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Khổ giấy:</span>{" "}
                <span className="tw-text-black">A4</span>
              </p>
              <p className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Hướng in:</span>{" "}
                <span className="tw-text-black">Dọc</span>
              </p>
              <div className="tw-mb-4">
                <span className="tw-font-bold tw-text-black">Căn lề:</span>
                <div className="tw-text-black">
                  <p className="tw-inline-block tw-mr-6 tw-text-black">Top: 10mm</p>
                  <p className="tw-inline-block tw-text-black">Bottom: 10mm</p>
                </div>
                <div className="tw-text-black">
                  <p className="tw-inline-block tw-mr-6 tw-text-black">Left: 15mm</p>
                  <p className="tw-inline-block tw-text-black">Right: 15mm</p>
                </div>
              </div>
            </div>
          </div>

          <div className="tw-flex tw-flex-col tw-items-center">
            <p className="tw-ml-6 tw-mr-6">
              <span className="tw-font-bold tw-text-black">Lưu trong 7 ngày:</span>{" "}
              <span className="tw-text-black">Có</span>
            </p>
          </div>

          <div className="tw-mt-6 tw-flex tw-justify-center">
            <button
              className="tw-bg-blue-500 tw-text-white tw-px-6 tw-py-2 tw-rounded-full hover:tw-bg-blue-700"
              onClick={() => handleDownload(selectedDocument)}
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

export default CustomerInfo;
