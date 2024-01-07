import './App.css'
import {useEffect, useState} from "react";
import logo from "./assets/wave.svg";
import {
    AimOutlined,
    HomeOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PlusOutlined,
    TeamOutlined,
    UserOutlined
} from "@ant-design/icons";
import {Routes} from "react-router-dom";

import {Button, DatePicker, Form, Input, Layout, Menu, Modal, Select, Space} from "antd";
import {utils} from "./utils/_helper.tsx";
import {createCompetition, getCompetitions} from "./utils/requests.tsx";

function App() {

    const [collapsed, setCollapsed] = useState(false);
    const [competitions, setCompetitions] = useState<any[]>([]);
    const [openCompetitionModal, setOpenCompetitionModal] = useState(false);
    const [form] = Form.useForm();

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
        }, {
            key: "4",
            url: "/targets",
            icon: <AimOutlined/>,
            label: "Cibles",
        },
    ];


    const handleFinish = (values: any) => {
        form.validateFields().then(() => {
                createCompetition(values).then((res) => {
                        setOpenCompetitionModal(false);
                        form.resetFields();
                        let competition = res.data;
                        setCompetitions([...competitions, {
                            label: `${competition.name} (${competition.date})`,
                            value: competition.id,
                        }])
                    }
                );
            }
        );
    }

    return (
        <>
            <Modal centered={true}
                   open={openCompetitionModal} onOk={
                form.submit
            } onCancel={() => {
                setOpenCompetitionModal(false)
            }}>

                <Form onFinish={
                    handleFinish
                } form={form}>
                    <Form.Item label={"Nom"} name={"name"} rules={
                        [
                            {
                                required: true,
                                message: "Veuillez saisir un nom"
                            }
                        ]
                    }>
                        <Input></Input>
                    </Form.Item>
                    <Form.Item label={"Date"} name={"date"} rules={
                        [
                            {
                                required: true,
                                message: "Veuillez saisir une date"
                            }
                        ]
                    }>
                        <DatePicker format={"DD/MM/YYYY"}></DatePicker>
                    </Form.Item>
                    <Form.Item label={"Description"} name={"description"}>
                        <Input></Input>
                    </Form.Item>
                </Form>

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
                                <img style={{
                                    flex: 1, objectFit: "contain", height: '100%',
                                    width: '100%'
                                }} alt={"logo"} src={logo}/>
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
                        <Layout.Header style={{background: '#fff', paddingLeft: '24px'}}>

                            <Space>
                                <Button type="primary" icon={
                                    collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>
                                } onClick={() => setCollapsed(!collapsed)}>

                                </Button>
                                <Select showSearch placeholder="Veuillez sélectionner une compétition"
                                        options={competitions}
                                        onChange={(value) => {
                                            utils.competition = value;
                                            utils.navigate("/");
                                        }}
                                        value={utils.competition}
                                >


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
