import { createContext } from "react";

const MyContact = createContext({
    contacts: [],
    setContacts: () => {}
  });
export default MyContact;
