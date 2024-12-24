import { create } from "zustand";


const defaultFileConfig = {
    paperType: "A3",
    numOfCopies: 1,
    isLandscape: false,
    fromPage: 1,
    toPage: 10,
    leftSide: 5,
    rightSide: 5,
    topSide: 5,
    bottomSide: 5,
    isOneSide: false,
    numOfPageOneSide: 2,
  };
export const useTransactionStore = create(set=>({
    printerId:"",
    name:"",
    newDocuments:[],
    oldDocuments:[],
    initTransaction: (name, newFiles, oldFiles) =>set(()=>({
        name,
        newDocuments: newFiles.map((newFile)=>({
            metadata:{
                name: newFile.name,
                save: true,
                detail:{
                    ...defaultFileConfig,
                }
            },
            file: newFile,
        })),
        oldDocuments: oldFiles.map((oldFile)=>({
            metadata:{
                id: oldFile.id,
                name: oldFile.name,
                detail: {
                    ...defaultFileConfig,
                }
            }
        }))
    })),
    setPrinter:(printerId)=>set(()=>({
        printerId,
    })),
    updateDocumentSetting: (idx,key,value,isNew)=>set((state)=>{
        const updatedDocuments = isNew? [...state.newDocuments]:[...state.oldDocuments];
        console.log(updatedDocuments);
        updatedDocuments[idx].metadata.detail[key] = value;
        return isNew?
                {newDocuments: updatedDocuments}:
                {oldDocuments: updatedDocuments}
    })
}));