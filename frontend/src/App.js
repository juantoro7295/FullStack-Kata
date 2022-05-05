import './App.css';
import React, { useContext, createContext, useEffect, useReducer, useRef, useState } from 'react';

const HOST_API = "http://localhost:8080/api/todo";
const initialState = {
  list: [],
  item: {}
};

const Store = createContext(initialState);

const Form = () => {
  const formRef = useRef(null);
  const { dispatch, state: { item } } = useContext(Store);
  const [state, setState] = useState(item);

  const onAdd = (event) => {
    event.preventDefault();

    const request = {
      name: state.name,
      id: null,
      isCompleted: false,
    };

    fetch(HOST_API + "/save", {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((todo) => {
        dispatch({ type: "add-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  };



  const onEdit = (event) => {
    event.preventDefault();

    const request = {
      name: state.name,
      id: item.id,
      completed: item.completed,
    };

    fetch(HOST_API + "/update", {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((todo) => {
        dispatch({ type: "update-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  };


  return (
    <form ref={formRef}>
      <input
        className="input-group mb-3"
        placeholder="agregue una tarea"
        type="text"
        name="name"
        defaultValue={item.name}
        onChange={(event) => {
          setState({ ...state, name: event.target.value });
        }}
      ></input>
      {
        item.id && <button className="btn btn-primary" onClick={onEdit}>Actualizar</button>
      }
      {
        !item.id && <button className="btn btn-primary" onClick={onAdd}>Agregar</button>
      }
    </form>
  );
};



const List = () => {
  const { dispatch, state } = useContext(Store);

  useEffect(() => {
    fetch(HOST_API + "/list")
      .then((response) => response.json())
      .then((list) => {
        dispatch({ type: "update-list", list });
      });
  }, [state.list.length, dispatch]);


  const onDelete = (id) => {
    fetch(HOST_API + "/delete/" + id, {
      method: "DELETE",
    }).then((list) => {
      dispatch({ type: "delete-item", id });
    });
  };

  const onEdit = (todo) => {
    dispatch({ type: "edit-item", item: todo })
  };

  return (
    <table className="table">
      <thead>
        <tr>
          <td>ID</td>
          <td>Nombre</td>
          <td>Esta completado?</td>
        </tr>
      </thead>
      <tbody>
        {state.list.map((todo) => {
          return (
            <tr key={todo.id}>
              <td>{todo.id}</td>
              <td>{todo.name}</td>
              <td>{todo.isCompleted === true ? 'si' : 'No'}</td>
              <td>
                <button className="btn btn-warning" onClick={() => onEdit(todo)}>Editar</button>
              </td>
              <td>
                <button className="btn btn-danger" onClick={() => onDelete(todo.id)}>Eliminar</button>
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

function reducer(state, action) {
  switch (action.type) {
    case "update-item":
      const listUpdateEdit = state.list.map((item) => {
        if (item.id === action.item.id) {
          return action.item
        }
        return item
      })
      return { ...state, list: listUpdateEdit, item: {} }
    case "delete-item":
      const listUpdate = state.list.filter((item) => {
        return item.id !== action.id;
      });
      return { ...state, list: listUpdate };
    case "update-list":
      return { ...state, list: action.list };
    case "edit-item":
      return { ...state, item: action.item };
    case "add-item":
      const newList = state.list;
      newList.push(action.item);
      return { ...state, list: newList };
    default:
      return state;
  }
}

const StoreProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <Store.Provider value={{ state, dispatch }}>{children}</Store.Provider>
  );
};



function App() {

  return (
    <div className="container" >
      <StoreProvider>
        <Form />
        <List />
      </StoreProvider>

    </div>

  );
};
export default App;


