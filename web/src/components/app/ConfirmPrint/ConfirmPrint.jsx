import axios from 'axios';
import '../../../input.css';
import { useTransactionStore } from '../Printsetting/PrintTransactionStore';
import { useNavigate } from 'react-router-dom';
import { apiBaseUrl } from '../../../config';

const TransactionConfirmation = () => {
  const {newDocuments, name, printerId,oldDocuments} = useTransactionStore();
  console.log(printerId);
  const navigate = useNavigate();
  const files = [...newDocuments,...oldDocuments].map((document)=>({
    name: document.metadata.name,
    paperType: document.metadata.detail.paperType,
    numOfCopies: document.metadata.detail.numOfCopies,
    save: true,
  }));
  const handleCreateTransaction = async ()=>{
    const createTransaction = async() =>{
      const api = `${apiBaseUrl}/transactions/create`;
      const token = localStorage.getItem("accessTokenCustomer");
      const body= {
        printerId,
        name,
        newDocuments: newDocuments.map((document)=>document.metadata),
        oldDocuments: oldDocuments.map((document)=>document.metadata),
      }
      console.log(body.oldDocuments);
      const res = await axios.post(api,body,{
        headers:{
          Authorization: `Bearer ${token}`
        }
      });
      if(res.status === 201){
        await  Promise.all(newDocuments.map(async (document,idx) =>{
          const formData = new FormData();
          formData.append("file",document.file);
          await axios.put(res.data.data.urls[idx],formData,{
            headers: {
              "Content-Type": "multipart/form-data", // Adjust based on file type
              // "Content-Length": document.file.size, // Set Content-Length explicitly
            },
          });
        }));
        navigate(`/customer/print/detail/${res.data.data.id.value}`)
      } else {
        console.log("Faileddd");
      }
    }
    await createTransaction();
  }

  const handleBack = () => {
    navigate('/upload/printsetting', {replace: true});
  }
  return (
    <div className="transaction-confirmation-page">
      <div className="container">
        <h1>Xác nhận giao dịch</h1>
        <p>Thời gian in dự kiến: <strong>10 phút</strong></p>
        <p>Thời gian hoàn thành dự kiến: <strong>10:45, 15/10/2023</strong></p>
        <p>Số lượng file: <strong>3</strong></p>
        <table>
          <thead>
            <tr>
              <th>Tên tài liệu</th>
              <th>Loại giấy</th>
              <th>Số bản</th>
              <th>Lưu</th>
            </tr>
          </thead>
          <tbody>
            {files.map((file, index) => (
              <tr key={index}>
                <td><a href="#">{file.name}</a></td>
                <td>{file.paperType}</td>
                <td>{file.numOfCopies}</td>
                <td className={file.save ? "status-valid" : !file.save ? "status-invalid" : "status-na"}>
                  {file.save ? "Yes" : !file.save ? "No" : "N/A"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="buttons">
          <button onClick={handleCreateTransaction}>Xác nhận</button>
          <button onClick={handleBack}>Quay lại</button>
        </div>
      </div>
    </div>
  );
};

export default TransactionConfirmation;
