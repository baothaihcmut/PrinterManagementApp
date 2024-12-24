import React from "react";
import { AiOutlineCheckCircle } from 'react-icons/ai';
import { useNavigate } from "react-router-dom";

const AdminLazyLoading = (props) => {
  const navigate = useNavigate();
  const { visibleData, status } = props;
  console.log(visibleData);
  const OnChange = props.OnChange;
  return (
    <table className="tw-mx-auto tw-border-separate tw-border tw-border-slate-200 tw-rounded-2xl tw-border-spacing-0 tw-overflow-hidden tw-w-full">
      <thead className="tw-bg-gray-100">
        <tr>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">ID</th>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Transaction Name</th>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Customer Name</th>
          {status === 'pending'? null : <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Employee Name</th>}
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">CreatedAt</th>
        </tr>
      </thead>

      <tbody>
        {visibleData.map((item, index) => {
          const commentButton = (() => {
            switch (status) {
              case "pending":
                return "Confirm";
              case "done":
                return "Detail";
              case "failure":
                return "Order again";
              case "process":
                return "Done";
              default:
                return "";
            }
          })();
          console.log(item.customerName.fir);
          return index === visibleData.length ? (
            <tr
            onClick={()=>navigate(`/admin/print/detail/${item.id}`)} 
            className="hover:tw-bg-cyan-50"
            key={index}>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.transactionId}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.name}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.customerName}</td>
              {status === 'pending'? null: <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.employeeName}</td>}
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.createdAt}</td>
              {/* <td className="tw-w-28 tw-h-10 tw-py-3"
                onClick={() => handleChange(item)}>
                {
                  item.isChange ? <AiOutlineCheckCircle className="tw-text-green-500 tw-w-6 tw-h-auto" /> :
              
                  <button className={`tw-border tw-w-28 tw-h-10 tw-text-white tw-text-semibold tw-bg-blue-500 tw-rounded-full`}>{commentButton}</button>
                }
              </td> */}
            </tr>
          ) :
          (
            <tr
            onClick={()=>navigate(`/admin/print/detail/${item.id}`)} 
            className="hover:tw-bg-cyan-50"
            key={index}>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.transactionId}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.name}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.customerName}</td>
              {status === 'pending'?null:<td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.employeeName}</td>}
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.createdAt}</td>
              {/* <td 
                className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-border-slate-200"
                onClick={() => OnChange(item)}
              >
                {
                  item.isChange ? <AiOutlineCheckCircle className="tw-text-green-500 tw-w-6 tw-h-auto" /> :
              
                  <button className={`tw-border tw-w-28 tw-h-10 tw-text-white tw-text-semibold tw-bg-blue-500 tw-rounded-full`}>{commentButton}</button>
                }
              </td> */}
            </tr>
          )})}
      </tbody>
    </table>
  )
};

export default AdminLazyLoading;