import React from "react";
import { AiOutlineCheckCircle } from 'react-icons/ai';
import { Link, useNavigate } from "react-router-dom";

const CusLazyLoading = (props) => {
  const navigate =useNavigate();
  const { visibleData, status } = props;
  // const OnChange = props.OnChange;
  return (
    <table className="tw-mx-auto tw-border-separate tw-border tw-border-slate-200 tw-rounded-2xl tw-border-spacing-0 tw-overflow-hidden tw-w-full">
      <thead className="tw-bg-gray-100">
        <tr>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">ID</th>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Transaction Name</th>
          <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Employee Name</th>
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

          return index === visibleData.length - 1 ? (
            <tr 
            onClick={()=>navigate(`/customer/print/detail/${item.id}`)}
            className="hover:tw-bg-cyan-50"
            key={index}>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center" >
               {item.transactionId}
              </td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.name}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.employeeName}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center">{item.createdAt}</td>
              
            </tr>
          ) :
          (
            <tr 
            className="hover:tw-bg-cyan-50"
            key={index}>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-text-center ">{item.transactionId}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.name}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.employeeName}</td>
              <td className="tw-w-28 tw-h-10 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.createdAt}</td>
              
            </tr>
          )})}
      </tbody>
    </table>
  )
};

export default CusLazyLoading;