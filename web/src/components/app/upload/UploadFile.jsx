
import { useEffect, useRef, useState } from 'react';
import {  useNavigate } from 'react-router-dom';
import { FaRegTrashCan } from "react-icons/fa6";
import { IoCloudUploadOutline } from "react-icons/io5";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { AiOutlineClose, AiOutlineCheckCircle, AiOutlineHistory } from 'react-icons/ai';
import Nav from '../../layout/Nav';
import axios from 'axios';
import { useTransactionStore } from '../Printsetting/PrintTransactionStore';
import { apiBaseUrl } from '../../../config';



const FileUpload = () => {
  const [files, setFiles] = useState([]);

  const [uploadedFile, setUploadedFile] = useState();

  const navigate = useNavigate();
  const fileInputRef = useRef(null);
  const {initTransaction} = useTransactionStore();

  useEffect(()=>{
    const fetchDocmuments = async ()=>{
      const api = `${apiBaseUrl}/customers/documents`;
      const token = localStorage.getItem("accessTokenCustomer");
      const res = await axios.get(api,{
        headers: {
          Authorization: `Bearer ${token}`,
        }
      });
      if(res.status ==200) {
        setUploadedFile(res.data.data.map((document)=>({
          id: document.id.value,
          name: document.name,
        })));
      } else {
        alert(res.data.message);
      }
    }
    fetchDocmuments();
  },[]);

  const handleFiles = (event) => {
    const selectedFiles = Array.from(event.target.files);
    uploadFiles(selectedFiles);
    event.target.value = null;
  };

  const handleDrop = (event) => {
    event.preventDefault();
    const droppedFiles = Array.from(event.dataTransfer.files);
    uploadFiles(droppedFiles);
    event.dataTransfer.clearData();
  };

  const uploadFiles = (filesToUpload) => {
    // initTransaction(filesToUpload[0].name,filesToUpload,[]);
    const newFiles = filesToUpload instanceof Array ? filesToUpload.map((file) => ({
      name: file.name,
      size: file.size,
      uploadedSize: 0,
      isCompleted: false,
      isNew: true
    })) : [
      {
      id: filesToUpload.id,
      name: filesToUpload.name,
      isNew:false,
      }
    ];


    newFiles.filter((item) => item.isNew).forEach((file) => {
      const interval = setInterval(() => {
        setFiles((prevFiles) =>
          prevFiles.map((f) =>
            f.name === file.name
              ? {
                  ...f,
                  uploadedSize: Math.min(f.uploadedSize + file.size / 20, file.size),
                  isCompleted: f.uploadedSize + file.size / 20 >= file.size,
                }
              : f
          )
        );
        

        setTimeout(() => {
          clearInterval(interval);
        }, 5000); 
      }, 100);
    });

    setFiles((prevFiles) => [...prevFiles, ...newFiles]);

  };

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleDeleteFile = (file) => {
    setFiles(prev => prev.filter((f) => f.name !== file.name));
  }

  const handleCheckBox = (e,item) => {
   if(!e.target.checked){
    setFiles(prev => prev.filter((f) => f.name !== item.name));
   }
   else {
    uploadFiles(item);
   }
  }

  const handleOnClick = () => {
    if(!files || files.length <= 0) {
      toast.warn('No files to upload', {
        position: "top-center"
      });
      return;
    }
    const fileTransaction = [...files];
    const newFile = fileTransaction.filter((fileResult)=>fileResult.isNew);
    const oldFile = fileTransaction.filter((fileResult)=>!fileResult.isNew);
    
    initTransaction(fileTransaction[0].name,newFile,oldFile);
    navigate('/upload/printer' , {replace: true})
  }

  return (
    <>
    <Nav />
    <div className='tw-ml-[240px] tw-mt-[73px] tw-grid tw-grid-cols-4'>
      <div className='tw-max-h-min tw-mx-auto tw-rounded-2xl tw-shadow-md tw-bg-white tw-p-6 tw-mt-8'>
        <div className='tw-relative tw-line-after tw-flex'>
          <AiOutlineHistory className='tw-inline-block tw-text-2xl tw-text-customBlue tw-mr-2 tw-border-solid tw-border-2 tw-rounded-full tw-p-0.5'/>
          <h2 className='tw-z-0 tw-text-lg tw-inline-block tw-text-customBlue'>Uploaded files</h2>
        </div>
        
        <div>
          {uploadedFile && uploadedFile.length > 0 && uploadedFile.map((file) => {
            return (
              <div key={file.id} className='tw-flex tw-space-x-3'>
                <input 
                  type="checkbox"
                  value={file.name}
                  onChange={(e) => handleCheckBox(e,file)} 
                  checked={files.some((o) => o.name === file.name)} 
                />
                <p className='tw-mb-0'>{file.name}</p>
              </div>
            )
          })}
        </div>
      </div>

      {/* content */}
      <div className="tw-col-start-2 tw-col-end-5 tw-p-6 tw-min-w-[70%] tw-min-h-max tw-ml-2 tw-mr-[40%] tw-bg-white tw-rounded-3xl tw-shadow-md tw-mt-8 ">
        <div className='tw-flex'>
          <IoCloudUploadOutline className="tw-text-customBlue tw-text-5xl tw-z-0 tw-border-solid tw-border-2 tw-rounded-full tw-p-2 tw-mr-3 tw-flex-none "/>
          <div className='tw-flex-1 tw-w-auto'>
            <h2 className="tw-text-lg tw-text-customBlue">Upload files</h2>
            <p className="tw-text-sm tw-text-customPurple">Select and upload the files of your choice</p>
          </div>
          <AiOutlineClose className="tw-flex-initial tw-ml-2 tw-border tw-p-0.5 tw-cursor-pointer tw-text-blue-300 tw-border-blue-300 tw-rounded-full" />
        </div>

        <div className="tw-w-full tw-h-0.5 tw-bg-gray-300 tw-my-4"></div>

        <div id='fileUploadContainer'
          className="tw-border-dashed tw-border-2 tw-border-gray-300 tw-mt-12 tw-p-6 tw-text-center tw-cursor-pointer tw-rounded-lg "
          onDrop={handleDrop}
          onDragOver={(e) => e.preventDefault()}
        >
          <IoCloudUploadOutline className="tw-text-customBlue tw-text-4xl tw-mx-auto tw-mb-4"/>
          <div className=' tw-mt-4'>
            <p className="tw-text-customBlue">Choose a file or drag & drop it here</p>
            <p className="tw-text-xs tw-text-customPurple tw-mt-2">DOC, PDF, up to 5MB</p>
          </div>
          
          <button
            className="tw-mt-5 tw-px-4 tw-py-2 tw-bg-white tw-text-customBlue tw-rounded-md tw-border-solid tw-border"
            onClick={handleClick}
          >
            Browse File
          </button>
          <input ref={fileInputRef} type="file" className="tw-hidden" onChange={handleFiles} multiple />
        </div>
        
        <div className="tw-mt-4">
          {files.map((file, index) => (
            <div
              key={index}
              className="tw-flex tw-items-center tw-justify-between tw-bg-[#f1f2f3] tw-p-2 tw-rounded-2xl tw-shadow tw-my-2"
            >
              <div className="tw-flex tw-items-center">
                <span className="tw-mr-2 tw-text-red-600">PDF</span>
                <div>
                  <div className="tw-font-medium tw-text-gray-700">{file.name}</div>
                  <div className="tw-text-xs tw-text-opacity-10 tw-text-black">
                    {Math.round(file.uploadedSize / 1024)} KB of {Math.round(file.size / 1024)} KB
                  </div>
                </div>
              </div>
              <div className="tw-flex tw-items-center">
                {!file.isCompleted ? file.isNew ? (
                  <progress
                    className="tw-w-24 tw-h-2 tw-mr-2 tw-rounded"
                    value={file.uploadedSize}
                    max={file.size}
                  ></progress>
                ):<AiOutlineCheckCircle className="tw-text-green-500 tw-mr-2" /> : (
                  <AiOutlineCheckCircle className="tw-text-green-500 tw-mr-2" />
                )}
                <span className="tw-text-xs">
                  {file.isCompleted ? (
                    <span className="tw-text-green-600 tw-block">Completed</span>
                  ) : file.isNew?(
                    <span className="tw-text-blue-600">Uploading...</span>
                  ):
                  <span className="tw-text-green-600 tw-block">Completed</span>

                  }
                </span>
                {file.isCompleted ? (
                  <FaRegTrashCan 
                  onClick={() => handleDeleteFile(file)}
                  className='tw-text-xs tw-ml-2 tw-cursor-pointer tw-block tw-text-red-500'/>
                ) : (
                  <AiOutlineClose 
                  onClick={() => handleDeleteFile(file)}
                  className="tw-ml-2 tw-cursor-pointer tw-text-red-500 " /> 
                )}
              </div>
            </div>
          ))}
          <div className='tw-flex tw-items-center tw-justify-center'>
            <button className='tw-flex tw-mx-auto tw-font-semibold tw-text-base tw-text-white tw-bg-blue-500 tw-px-[30px] tw-py-[14px] tw-rounded-[30px] tw-transition hover:tw-translate-y-1'
             onClick={() => handleOnClick()}>
              Confirm
            </button>
          </div>
        </div>
      </div>
    </div>
    <ToastContainer />
    </>
  );
};

export default FileUpload;
