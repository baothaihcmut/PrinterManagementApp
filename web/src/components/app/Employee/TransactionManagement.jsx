
import React, { useState } from "react";
import Nav from '../../layout/Nav';

const transactionList = [
  {id: 1, name: "transaction1", status:"pending" , customerName: "TB"},
  {id: 2, name: "transaction2", status:"process" , customerName: "CC"},
  {id: 3, name: "transaction3", status:"failure", customerName: "KL"},
  {id: 4, name: "transaction4", status:"done", customerName: "XC"},
]

const TransactionManagement = () => {
  const [status, setStatus] = useState("pending");

  return (
    <>
      <Nav />
      <div className="tw-mt-[73px] tw-ml-[240px]">

        {/* status gd */}
        <div className="tw-mt-36">
          <h2 className="tw-text-center tw-text-customBlue tw-font-semibold tw-mb-6">Transaction Management</h2>
          <div className="tw-flex tw-mx-auto tw-w-[90%] tw-h-20 tw-border tw-rounded-full tw-overflow-hidden">
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "pending" ? "tw-bg-blue-100 tw-text-customBlue" : "tw-text-gray-500 tw-bg-white"}`}
            onClick={() => setStatus("pending")}
            >Pending</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "process" ? "tw-bg-blue-100 tw-text-customBlue" : "tw-text-gray-500 tw-bg-white"}`}
            onClick={() => setStatus("process")}
            >Process</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "failure" ? "tw-bg-blue-100 tw-text-customBlue" : "tw-text-gray-500 tw-bg-white"}`}
            onClick={() => setStatus("failure")}
            >Failure</button>
            <button className={`tw-inline-flex tw-flex-1 tw-items-center tw-justify-center tw-px-6 ${status === "done" ? "tw-bg-blue-100 tw-text-customBlue" : "tw-text-gray-500 tw-bg-white"}`}
            onClick={() => setStatus("done")}
            >Done</button>
          </div>

        </div>

        {/* list gd */}
        <div className="tw-mt-12">
          <table className="tw-mx-auto tw-border-separate tw-border tw-border-slate-200 tw-rounded-md tw-border-spacing-0 tw-overflow-hidden tw-w-[90%]">
            <thead className="tw-bg-gray-100">
              <tr>
                <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">ID</th>
                <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Transaction Name</th>
                <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Customer Name</th>
                <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center">Status</th>
                <th className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200 tw-text-center"></th>
              </tr>
            </thead>

            <tbody>
              {transactionList.map((item, index) => {
                const statusClass = (() => {
                  switch (item.status.toLowerCase()) {
                    case "pending":
                      return "tw-bg-orange-300 tw-text-white";
                    case "process":
                      return "tw-bg-blue-400 tw-text-white";
                    case "done":
                      return "tw-bg-green-400 tw-text-white";
                    case "failure":
                      return "tw-bg-red-400 tw-text-white";
                    default:
                      return "tw-bg-gray-400 tw-text-white";
                  }
                })();

                const commandButton = (() => {
                  switch (item.status.toLowerCase()) {
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

                return index === transactionList.length - 1 ? (
                  <tr key={item.id}>
                    <td className="tw-px-8 tw-py-3 tw-text-center">{item.id}</td>
                    <td className="tw-px-8 tw-py-3 tw-text-center">{item.name}</td>
                    <td className="tw-px-8 tw-py-3 tw-text-center">{item.customerName}</td>
                    <td className="tw-px-8 tw-py-3 tw-text-center">
                      <span className={`tw-inline-flex tw-items-center tw-justify-center tw-min-w-32 tw-h-10 tw-text-sm tw-font-semibold tw-rounded-full ${statusClass}`}>{item.status}</span>
                    </td>
                    <td className="tw-px-8 tw-py-3">
                      <button className={`tw-inline-flex tw-border tw-border-slate-200 tw-rounded-full tw-items-center tw-justify-center tw-w-28 tw-h-8 tw-bg-blue-500 tw-text-white tw-font-semibold`}>{commandButton}
                      </button>
                    </td>
                  </tr>
                ) :
                (
                  <tr key={item.id}>
                    <td className="tw-px-8 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.id}</td>
                    <td className="tw-px-8 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.name}</td>
                    <td className="tw-px-8 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">{item.customerName}</td>
                    <td className="tw-px-8 tw-py-3 tw-border-b tw-text-center tw-border-slate-200">
                      <span className={`tw-inline-flex tw-items-center tw-justify-center tw-min-w-32 tw-h-10 tw-text-sm tw-font-semibold tw-rounded-full ${statusClass}`}>{item.status}</span>
                    </td>
                    <td className="tw-px-8 tw-py-3 tw-border-b tw-border-slate-200">
                      <button className={`tw-inline-flex tw-border tw-border-slate-200 tw-rounded-full tw-items-center tw-justify-center tw-w-28 tw-h-8 tw-bg-blue-500 tw-text-white tw-font-semibold`}>{commandButton}
                      </button>
                    </td>
                  </tr>
                )})}
            </tbody>
          </table>
        </div>

      </div>
    </>
  )
};

export default TransactionManagement;