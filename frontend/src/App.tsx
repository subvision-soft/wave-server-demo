import './App.css'
import {useEffect, useState} from "react";
import logo from "./assets/wave.svg";
import { HomeOutlined, PlusOutlined, UserOutlined,TeamOutlined,AimOutlined,MenuFoldOutlined,MenuUnfoldOutlined} from "@ant-design/icons";
import {Routes} from "react-router-dom";

import {Button, Layout, Menu, Select, Space,Modal} from "antd";
import {utils} from "./utils/_helper.tsx";
import {getCompetitions} from "./utils/requests.tsx";

function App() {

    const [collapsed,setCollapsed] = useState(false);
    const [competitions,setCompetitions] = useState([]);
    const [openCompetitionModal, setOpenCompetitionModal] = useState(false);
    useEffect(() => {
        getCompetitions().then((res) => {
            let competitions = res.data.map((competition: { name: any; date: any; id: any; }) => {
                return {
                    label: `${competition.name} (${competition.date})`,
                    value: competition.id,
                };
            });
            setCompetitions(competitions);
        });
    }, []);

    useEffect(() => {

    }, [location]);

    const [selectedKey, setSelectedKey] = useState("0");




    const items = [
        {
            key: "0",
            url: "/",
            icon: <HomeOutlined/>,
            label: "Accueil",
        },
        {
            key: "2",
            url: "/users",
            icon: <UserOutlined/>,
            label: "Compétiteurs",
        },
        {
            key: "3",
            url: "/teams",
            icon: <TeamOutlined/>,
            label: "Equipes de relais",
        },        {
            key: "4",
            url: "/targets",
            icon: <AimOutlined />,
            label: "Cibles",
        },
    ];

    return (
        <>
            <Modal open={openCompetitionModal} onOk={
                () => {
                    setOpenCompetitionModal(false)
                }
            } onCancel={() => {
                setOpenCompetitionModal(false)
            }}>

            </Modal>
            <Layout style={{height: "100vh"}}>
                <Layout style={{height: "100%"}}>
                    <Layout.Sider
                        theme={"dark"}
                        trigger={null}
                        collapsible
                        collapsed={collapsed}
                    >

                        <Layout.Content>
                            <div
                                className="logo"
                                style={{
                                    display: "flex",
                                    flexDirection: "column",
                                    alignItems: "stretch",
                                    padding: "20px",
                                    height: "60px",
                                    width: "100%",

                                }}
                            >
                                <img style={{flex:1,objectFit: "contain",height: '100%',
                                    width: '100%'}} alt={"logo"} src={logo}/>
                            </div>
                            <Menu
                                mode="inline"
                                defaultSelectedKeys={["1"]}
                                items={items}
                                theme="dark"
                                onSelect={(e) => {
                                    setSelectedKey(e.key);
                                    utils.navigate(items.find((item) => item.key === e.key)!.url);
                                }
                                }
                                selectedKeys={[selectedKey]}
                            />
                        </Layout.Content>
                    </Layout.Sider>
                    <Layout className="site-layout">
                        <Layout.Header  style={{background: '#fff',paddingLeft:'24px'}}>

                            <Space>
                                <Button type="primary" icon={
                                    collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>
                                } onClick={() => setCollapsed(!collapsed)}>

                                </Button>
                                <Select showSearch placeholder="Veuillez sélectionner une compétition" options={competitions}>

                                </Select>

                                <Button
                                    onClick={() => setOpenCompetitionModal(true)}

                                    icon={<PlusOutlined></PlusOutlined>}
                                ></Button>
                            </Space>
                        </Layout.Header>
                        <Layout.Content>
                            <Routes>
                            </Routes>
                        </Layout.Content>
                    </Layout>
                </Layout>
            </Layout></>
    )
}

export default App
