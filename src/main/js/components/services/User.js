import axios from "axios";

const baseUrl = "http://localhost:8090/api/user";
const user = {};

user.create = async (state) => {
  const data = {
    username: state.username,
    password: state.password,
    email: state.email,
  };

  const urlPost = baseUrl + "/create";

  const res = await axios
    .post(urlPost, data)
    .then((response) => {
      const data = { success: true, message: response.data };
      return data;
    })
    .catch((error) => {
      const data = { success: false, message: error.response.data };
      return data;
    });

  return res;
};

export default user;
