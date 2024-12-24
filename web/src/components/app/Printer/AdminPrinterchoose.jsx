
import Header from "../../layout/Nav";
import Footer from "../../layout/Footer";
import PrinterImg from '../../../assets/images/logo-new.png';
import {  useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import axios from "axios";
import { apiBaseUrl } from '../../../config';
import { IoAddSharp, IoLocation } from "react-icons/io5";
import { CiUser } from "react-icons/ci";
import { LuPrinterCheck } from "react-icons/lu";
import { FaLaptopCode } from "react-icons/fa6";



const Printer = () => {
  const navigate = useNavigate();
  const [printerList,setPrinterList] = useState([]);
  const [newPrinter, setNewPrinter] = useState({
    status: 'ONLINE'
  });
  const [isTrigger, setIsTrigger] = useState(false);
  const [isUpdate, setIsUpdate] = useState(false);
  const [updatePrinter,setPrinterUpdate] = useState({});
  useEffect(()=>{
    const fetchPrinterList = async ()=>{
      const api = `${apiBaseUrl}/printers`;
      const token = localStorage.getItem("accessTokenAdmin");
      const page = 1;
      const size = 10;
      const res = await axios.get(api, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          page,
          size,
        }
      });
      if(res.status === 200) {
        setPrinterList(res.data.data.data.map((printer)=>({
          id: printer.id.value,
          name: printer.name,
          code: printer.code,
          location: printer.location,
          status: printer.status,
        })))
      } else{
        alert(res.data.message);
      }
    }
    fetchPrinterList();
  },[]);

  const handleBack = () => {
    navigate('/upload', {replace: true});
  }

  
    const handleOnAdd = async () => {
    console.log('check', newPrinter);
    const api = `${apiBaseUrl}/printers/create`;
    const token = localStorage.getItem("accessTokenAdmin");
    console.log(newPrinter);
    const res = await axios.post(api,{
      name: newPrinter.name,
      location: newPrinter.location,
      code: newPrinter.code,
      status: newPrinter.status,
    },{
      headers: {
        Authorization: `Bearer ${token}`
      },
    });
    if(res.status === 201) {
      const newPrinterRes = res.data.data
      setPrinterList([...printerList, {
        id: newPrinterRes.id.value,
        name: newPrinterRes.name,
        location: newPrinterRes.location,
        code: newPrinterRes.code,
        status: newPrinterRes.status,
      }]);
      setIsTrigger(false);
    }

  }

  const handleGetInfoUpdate = async (printer) =>{
    if(!isUpdate)  {
        setPrinterUpdate({
          id: printer.id,
          name: printer.name,
          location: printer.location,
          status: printer.status,
        });
    }
    setIsUpdate(!isUpdate);
  }
  const handleOnUpdate = async () => {
    const api = `${apiBaseUrl}/printers/update/${updatePrinter.id}`;
    const token = localStorage.getItem("accessTokenAdmin");
    const res = await axios.put(api,{
      name: updatePrinter.name,
      location: updatePrinter.location,
      status: updatePrinter.status,
    },{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(res.status === 201) {
      const updateRes = res.data.data;
      setPrinterList(printerList.map((printer) =>(
        printer.id === updateRes.id.value ? {
          id: updateRes.id,
          name: updateRes.name,
          location: updateRes.location,
          code: updateRes.code,
          status: updateRes.status,
        }: printer)));
      };
      setIsUpdate(false);
    }
  
  return (
    <>
    <Header />
    <div className="tw-relative tw-mt-28 tw-container tw-mx-auto tw-w-3/5 tw-p-6 tw-min-h-max tw-bg-white tw-rounded-3xl tw-shadow-md">
      <div className="tw-flex tw-items-center tw-absolute tw-top-0 tw-left-1/2 tw-transform -tw-translate-x-1/2">
        <img className="tw-w-20" src={PrinterImg} alt="logo"/>
        <p className="tw-text-center tw-text-customBlue tw-text-2xl tw-inline-block ">Choose Printer</p>
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
      <button 
        className="add-emp-button tw-text-customBlue tw-group tw-flex tw-items-center 
                    tw-rounded-md tw-text-sm tw-font-medium tw-px-4 tw-py-2 tw-ml-[85%]" 
        onClick={() => setIsTrigger(!isTrigger)} 
      >
        <IoAddSharp className='tw-mr-1'/>
        Add Printer
         
      </button>

      {isTrigger && 
        <div className="modal-add-emp">
          <div className="modal-add-content">
          <h3>Điền thông tin</h3>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <CiUser />
            <input
              type="text"
              placeholder="Name"
              className='tw-w-[90%]'
              value={newPrinter.name}
              onChange={(e) => setNewPrinter({ ...newPrinter, name: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <FaLaptopCode />
            <input
              type="text"
              placeholder="Code"
              className='tw-w-[90%]'
              value={newPrinter.code}
              onChange={(e) => setNewPrinter({ ...newPrinter, code: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
            <IoLocation />
            <input
              type="text"
              placeholder="Location"
              className='tw-w-[90%]'
              value={newPrinter.location}
              onChange={(e) => setNewPrinter({ ...newPrinter, location: e.target.value })}
            />
            </div>

            <div className='tw-flex tw-font-semibold tw-text-lg'> 
              <LuPrinterCheck />
              <select 
                className='tw-w-[90%]'
                defaultValue={'ONLINE'}
                onChange={(e) => setNewPrinter({ ...newPrinter, status: e.target.value })}>
                <option value={'ONLINE'}>Online</option>
                <option value={'OFFLINE'}>Offline</option>
                <option value={'ERROR'}>Error</option>
              </select>
            </div>

            <div className="modal-buttons">
              <button onClick={() => handleOnAdd()}>Confirm</button>
              <button onClick={() => setIsTrigger(!isTrigger)}>Cancel</button>
            </div>
          </div>
        </div>}
      <div className="tw-rounded-lg tw-shadow-md tw-mt-2 tw-bg-scroll tw-overflow-y-auto tw-h-52">
        <table className="tw-min-w-full tw-border-separate tw-border-spacing-0 tw-border tw-rounded-lg tw-overflow-hidden">
          <thead className="tw-bg-gray-100">
            <tr>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Tên máy in</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Mã máy in</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Vị trí</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Trạng thái</th>
              <th className="tw-px-6 tw-py-3 tw-border-b tw-text-left">Cập nhật</th>
            </tr>
          </thead>
          <tbody>
            {printerList.map((printer) => (
              <tr 
              key={printer.id} className="tw-border-b hover:tw-bg-cyan-50"
              onClick={()=>navigate(`/printer/infor/${printer.id}`)}
              >
                <td className="tw-px-6 tw-py-3">{printer.name}</td>
                <td className="tw-px-6 tw-py-3">{printer.code}</td>
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
                  <button className="add-emp-button tw-text-customBlue tw-group tw-flex tw-items-center 
                    tw-rounded-md tw-text-sm tw-font-medium tw-px-4 tw-py-2"
                    onClick={() => handleGetInfoUpdate(printer)}>
                    Update
                  </button>
                </td>

                {isUpdate && 
                <div className="modal-add-emp">
                  <div className="modal-add-content">
                  <h3>Cập nhật thông tin</h3>

                    <div className='tw-flex tw-font-semibold tw-text-lg'> 
                    <CiUser />
                    <input
                      type="text"
                      placeholder="Name"
                      className='tw-w-[90%]'
                      value={updatePrinter.name}
                      onChange={(e) => setPrinterUpdate({
                        ...updatePrinter,
                        name: e.target.value,
                      })}
                    />
                    </div>

                    <div className='tw-flex tw-font-semibold tw-text-lg'> 
                    <IoLocation />
                    <input
                      type="text"
                      placeholder="Location"
                      className='tw-w-[90%]'
                      value={updatePrinter.location}
                      onChange={(e) => setPrinterUpdate({
                        ...updatePrinter,
                        location: e.target.value
                      })}
                    />
                    </div>

                    <div className='tw-flex tw-font-semibold tw-text-lg'> 
                      <LuPrinterCheck />
                      <select 
                        className='tw-w-[90%]'
                        defaultValue={updatePrinter.status}
                        onChange={(e) => setPrinterUpdate({
                          ...updatePrinter,
                          status: e.target.value,
                        })}>
                        <option value={'ONLINE'}>Online</option>
                        <option value={'OFFLINE'}>Offline</option>
                        <option value={'ERROR'}>Error</option>
                      </select>
                    </div>

                    <div className="modal-buttons">
                      <button onClick={() => handleOnUpdate()}>Update</button>
                      <button onClick={() => setIsUpdate(!isUpdate)}>Cancel</button>
                    </div>
                  </div>
                </div>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="tw-flex tw-space-x-9 tw-items-center tw-justify-center tw-mt-8 ">
        <button 
          className="tw-border tw-border-gray-100 tw-rounded-md tw-text-customBlue tw-bg-white tw-p-1"
          onClick={handleBack}>Quay lại</button>
      </div>
    </div>
    
    <Footer />
    </>
  );
}

export default Printer;
