import axios from "axios";

const baseUrl = "http://localhost:8090/api/user";
const user = {};

user.list = async () => {
  const urlList = baseUrl + "/list";
  const res = await axios
    .get(urlList)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      return error;
    });
  return res;
};

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
      return response.data;
    })
    .catch((error) => {
      return error.response;
    });

  return res;
};

user.get = async (id) => {
  const urlGet = baseUrl + "/get/" + id;
  const res = await axios
    .get(urlGet)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      return error;
    });
  return res;
};

user.update = async (state) => {
  const data = {
    username: state.username,
    password: state.password,
    email: state.email,
  };

  const urlUpdate = baseUrl + "/update/" + state.id;

  const res = await axios
    .put(urlUpdate, data)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      return error.response;
    });

  return res;
};

user.delete = async (id) => {
  const urlDelete = baseUrl + "/delete/" + id;
  const res = await axios
    .delete(urlDelete)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      return error;
    });
  return res;
};

export default user;
