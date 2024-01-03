import { ExpandLess, ExpandMore } from "@mui/icons-material";
import { Collapse, List, ListItemButton, ListItemText, Typography } from "@mui/material";
import { Box } from "@mui/system";
import React, { useEffect, useState } from "react";
import GameTable from "../components/GameTable";
import {redirect} from "react-router";

function ListRow(row) {

    const [category, setCategory] = useState(row.row);
    const [open, setOpen] = useState(false);

    console.log(category);

    const handleClick = (e) =>{
        setOpen(!open);
    };

    return (
        <React.Fragment key={category.name}>
            <ListItemButton onClick={handleClick} sx={{borderBottom: 'solid #EEEEEE'}}>
                <ListItemText primary={category.name} />
                {open ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <GameTable data={category.boardGames} />
            </Collapse>
        </React.Fragment>
    );
}

export default function GamesByCategoryPage() {


    const [data, setData] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user") == null ? sessionStorage.getItem("user") : localStorage.getItem("user"));
        console.log(user);

        if (token === null || user === null) {
            window.location = '/';
        }

        fetch("http://localhost:8080/api/users/" + user.id + "/boardGames-by-category", {
            method: "GET", 
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then((response) => {
            console.log(response);
            if (response.status === 401) {
                window.location = '/';
                return Promise.reject(new Error());
            } else if (response.ok) {
                return response.json()
            }
        })
        .then((result) => {
            console.log(result)
            setData(result);
            console.log(result);
        }, (error) => console.log(error))
    }, []);

    const [open, setOpen] = useState({});

    const handleClick = (e) => {
        setOpen({[e.target.name]: e.target.value});
    };

    return (<Box
        sx={{
            margin: '2%'
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: '#fe2800'
                }}
            >
                Kategorie {data != null ? data.name : ''}
            </Typography>
            {data != null ? (
                <List
                    sx={{ width: '100%' }}
                    component="nav">
                        {
                        data.map((category) => (
                            <ListRow row={category}/>
                        ))}
                </List>
                ) : (<Typography>Ładowanie...</Typography>)}
        </Box>);
}