
import Header from "../../layout/Nav";
import Footer from "../../layout/Footer";
import PrinterImg from '../../../assets/images/logo-new.png';
import {  useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import axios from "axios";
import { apiBaseUrl } from '../../../config';





const ChoosePrinter = () => {
  const navigate = useNavigate();
  const [printerList,setPrinterList] = useState([]);
  const [selectedPrinter,setSelectedPrinter] = useState([]);
  useEffect(()=>{
    const fetchPrinterList = async ()=>{
      const api = `${apiBaseUrl}/employees/printers`;
      const token = localStorage.getItem("accessTokenEmployee");
      
      const res = await axios.get(api, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if(res.status === 200) {
        console.log(res.data.data[0].name)
        setPrinterList(res.data.data.map((printer)=>({
          id: printer.printerId.value,
          name: printer.printerName,
          code: printer.printerCode,
          location: printer.printerLocation,
          status: printer.printerStatus,
          numOfTransactionProcess: printer.numOfTransactionProcess,
          numOfTransactionDone: printer.numOfTransactionDone,
        })))
      } else{
        alert(res.data.message);
      }
    }
    fetchPrinterList();
  },[]);

  

  const handleOnClick = () => {
    navigate(`/employee/management/transaction/${selectedPrinter}`)
  }
  console.log(printerList);
  return (
    <>
    <Header />
    <div className="tw-relative tw-mt-28 tw-container tw-mx-auto tw-w-3/5 tw-p-6 tw-min-h-max tw-bg-white tw-rounded-3xl tw-shadow-md">
      <div className="tw-flex tw-items-center tw-absolute tw-top-0 tw-left-1/2 tw-transform -tw-translate-x-1/2">
        <img className="tw-w-20" src={PrinterImg} alt="logo"/>
        <p className="tw-text-center tw-text-customBlue tw-text-2xl tw-inline-block ">Choose Choose</p>
      </div>
      
      <div className="tw-mx-auto tw-text-center tw-text-customPurple tw-mt-16" >
        <label className="tw-text-center tw-mx-auto">
          Pick a location:
          <select>
            <option value="CS1">Lý Thường Kiệt</option>
            <option value="CS2">Thủ Đức</option>
          </select>
        </label>
      </div>
      
      <div className="tw-rounded-lg tw-shadow-md tw-mt-2 tw-bg-scroll tw-overflow-y-auto tw-h-52">
        <table className="tw-min-w-full tw-border-separate tw-border-spacing-0 tw-border tw-rounded-lg tw-overflow-hidden">
          <thead className="tw-bg-gray-100">
            <tr>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Tên máy in</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Mã máy in</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Số giao dịch đang xử xý</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Số giao dịch đã xong</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Vị trí</th>  
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Trạng thái</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Chọn</th>
            </tr>
          </thead>
          <tbody>
            {printerList.map((printer) => (
              <tr key={printer.id} className="tw-border-b">
                <td className="tw-px-6 tw-py-3">{printer.name}</td>
                <td className="tw-px-6 tw-py-3">{printer.code}</td>
                <td className="tw-px-6 tw-py-3">{printer.numOfTransactionProcess}</td>
                <td className="tw-px-6 tw-py-3">{printer.numOfTransactionDone}</td>
                <td className="tw-px-6 tw-py-3 tw-font-bold">{printer.location}</td>
                <td className="tw-px-6 tw-py-3">
                  <span
                    className={`tw-inline-block tw-px-3 tw-py-1 tw-border tw-text-sm tw-font-semibold tw-rounded-lg ${
                      printer.status === "ONLINE"
                        ? "tw-bg-green-100 tw-text-green-600"
                        : "tw-bg-red-100 tw-text-red-600"
                    }`}
                  >
                    {printer.status}
                  </span>
                </td>
                <td className="tw-px-6 tw-py-3">
                  <label className="tw-flex tw-items-center" onClick={()=>{setSelectedPrinter(printer.id)}}>
                    <input type="radio" name="printer" className="tw-mr-2" />
                    Xác nhận
                  </label>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="tw-flex tw-space-x-9 tw-items-center tw-justify-center tw-mt-8 ">
        <button 
          className="tw-border tw-border-gray-100 tw-rounded-md tw-text-customBlue tw-bg-white tw-p-1"
          >Quay lại</button>
        <button 
          className="tw-border tw-border-gray-100 tw-rounded-md tw-text-customBlue tw-bg-white tw-p-1"
          onClick={handleOnClick}>
            Xác Nhận
          </button>
      </div>
    </div>
    
    <Footer />
    </>
  );
}

export default ChoosePrinter;
