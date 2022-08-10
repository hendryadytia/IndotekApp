<script>
     import {apiurl} from '../helper/apiurl.js'
    import Topbar from '../components/Topbar.svelte'
    import Table from '../components/Table.svelte'
    import Card from '../components/Card.svelte'
    import { useNavigate, useLocation } from "svelte-navigator";
    import ORP from '../components/OvrReqPswd.svelte'
    import ORK from '../components/OvrReqKonfirm.svelte'
    import ORKL from '../components/OvrReqKonfirmLogout.svelte'
    import ORKW from '../components/OvrReqKonfirmWord.svelte'
    import FDM from '../components/forms/form_data_admin.svelte'
    import FDMAN from '../components/forms/form_data_manager.svelte'
    import ButtonSmall from "../components/ButtonSmall.svelte"
    import NotificationChanges from '../components/NotificationChanges.svelte'

    import X from 'jquery'


    // untuk logout pake svelte nav usenavi dan uselocation
    const navigate = useNavigate();
    const location = useLocation();
    const menu1 = 'daftarAdmin'
    const menu2 = 'setManage'
    const menu3 = 'backupData'
    const menu4 = 'hapusData'
    const menu5 = 'historyLogin'
    let triggernotif,bodynotif,headnotif,responsestatus
    let menuSetting
    let konfirmasiPassword
    let konfirmasiDelete
    let konfirmasiWord
    let konfirmasiLogout
    let tambahAdmin
    let visibilityOverlayKonfirmasi
    let visibilityOverlayKonfirmasiLogout
    let visibilityOverlayKonfirmasiWord
    let visibilityOverlayKonfirmasiDelete
    let tablehead
    let tableContext = 'Admin'

    let editDetail = false
    let detailPane  

    
    const logoutin = () => {
        navigate("/login", {
        state: { from: $location.pathname },
        replace: true});
    }
    console.log(getCookie('token'));
    const confirmLogout = () => {
        visibilityOverlayKonfirmasiLogout = true
    }
    
    const getTable = () => {
                
        X.ajax({
                type: "GET",
                beforeSend: function(xhr) {
                                            xhr.setRequestHeader('Authorization', getCookie('token'));
                                        },
                url: apiurl+"daftaradmin",
                processData: false,
                success: function(msg) {
                    console.log(msg);
                }
        });
    }


    getTable();
   

   

    function edit(diapain) {
       switch (diapain) {
           case 'tutup':
                editDetail = false       
               break;
            case 'buka':
                editDetail = true
                detailPane = 'edit'
               break;
       }
    }

    const requestMenu = (menu) => {
        menuSetting = menu
        konfirmasiPassword = false
        tambahAdmin = false
        if (menuSetting == menu1|| menuSetting== menu2 ) {
            visibilityOverlayKonfirmasi = true
        }else if (menu === 'close'){
            menuSetting = ''
            konfirmasiPassword = false
        }else if (menu == menu3 || menu == menu5){
            konfirmasiPassword = true
        }
        else if (menu == menu4){
            visibilityOverlayKonfirmasiDelete = true
        }

        switch (menu) {
            case menu1:
                tablehead = tableHeadDaftarAdmin
                dtblCFG =      {
                "ajax":{
                        "url": apiurl+"daftaradmin",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        }
                        
                    },
                columns: [
                        { data: 'id' },
                        { data: 'nama' },
                        { data: 'username' }
                    ],
                "columnDefs": [ {
                    "targets": -1,
                        "render" : function ( data, type, row ) {
                            return "<div class='acw' style='display:flex;justify-content:space-between'>" + data + " <img src='./icons/table_menu.svg' class='cxbtn' style='height:18px; width: 20px '/> </div>"
                        },
                        "defaultContent": "<button>C</button>"
                    },
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
              
                ]
            }
                break;
        
            case menu3:
                tablehead = tableHeadBackup
                tableContext = 'Loglogin'
                dtblCFG = {
                "ajax":{
                        "url": apiurl+"badckupdblist",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        }
                        
                    },
                columns: [
                        { data: 'id' },
                        { data: 'tanggal' },
                        { data: 'nama_file' },
                    ],
                "columnDefs": [ 
                  
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                    {
                        "targets": 1,
                        "render" : function ( data, type, row ) {
                            return data.length > 14 ? data.substring(0,13)+"..." : data
                        },
                    } ,
                    {
                        "targets": 2,
                        "render" : function ( data, type, row ) {
                            return data
                        },
                    
                    } ,
                   
                ]}
                break;
        
            case menu5:
                tablehead = tableHeadHistoryLogin
                tableContext = 'Loglogin'
                dtblCFG = {
                "ajax":{
                        "url": apiurl+"getloglogin",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        }
                        
                    },
                columns: [
                        { data: 'id' },
                        { data: 'id_akun' },
                        { data: 'tgl_login' },
                        { data: 'ip_address' },
         
                    ],
                "columnDefs": [ 
                  
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                 
                  
                ]
            }
                break;
        
            default:
                break;
        }
    }

    const requestFormTambahAdmin = () => {
        tambahAdmin = true
    }
    let id
    function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }
    let role = getCookie('jabatan')


    let tableHead = ['id',
                            'Jenis Mesin',
                            'Pemilik',
                            'Deadline',
                            'Teknisi',
                            'Status',
                        ]
    let tableHeadDaftarAdmin = ['id',
                            'Nama',
                            'Username'
                        ]
    let tableHeadBackup = ['id',
                            'Tanggal Backup',
                            'Nama File'
                        ]
    let tableHeadHistoryLogin = ['id',
                            'Nama Akun',
                            'Waktu',
                            'Alamat IP'
                        ]

    let dtblCFG;

            function backupdb() {
                X.ajax({
                    url:apiurl+"backupdb",
                    type:"get",
                    beforeSend: function (request) {
                                    request.setRequestHeader('Authorization', getCookie('token'));
                                },
                    success: function(result){
                        if(result.code == "200"){
                            triggernotif = "show"
                            bodynotif = "Berhasil Backup Data"
                            headnotif = "Backup"
                            responsestatus = "sukses"
                        }
                    },
                
                                
                    },
                )
            }
    
</script>

<div style="display: block; width: 100%; padding-left: 35px">

    <Topbar icon="setting" namaHalaman="Setting" marginBottom="40" />

    <div style="display: flex; gap:100px; width: 100%;" class="boddy">
        <Card useShadow>

            <div style="display: flex; flex-direction:column; gap:12px">
                {#if role === '1'}
                <p class="menus" on:click={()=>requestMenu(menu1)}>Daftar Admin</p>
                <p class="menus" on:click={()=>requestMenu(menu2)}>Setel ulang Manager</p>
                {/if}
                <p class="menus" on:click={()=>requestMenu(menu3)}>Backup Data</p>
                {#if role === '1'}
                <p class="menus" on:click={()=>requestMenu(menu4)}>Hapus Data</p>
                {/if}
                <p class="menus" on:click={()=>requestMenu(menu5)}>History Login</p>
                 <p class="menus" on:click={confirmLogout} >Logout</p>
            </div>
        </Card>
        {#if konfirmasiPassword && menuSetting != ''}
            <div>
                <div style="display: flex; justify-content:space-between; width: 100%; margin-bottom: 12px">
                    {#if menuSetting == menu1}
                        <ButtonSmall on:click={requestFormTambahAdmin}>Tambah Admin</ButtonSmall>
                    {:else if menuSetting == menu3}
                        <ButtonSmall on:click={backupdb}>Backup Sekarang</ButtonSmall>
                        
                    {/if}
                    <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-left:auto" on:click={() => {menuSetting = ''; konfirmasiPassword = false}}/>
                </div>
                    {#if menuSetting == menu1 || menuSetting == menu3 || menuSetting == menu5}
                    <Table  editDetail = { () => edit('buka')}  bind:id tableHead={tablehead} dataTableCFG={dtblCFG} context={tableContext} />
                    {:else if menuSetting == menu2}
                    <FDMAN withRemoveButton = {false} bind:formCount= {menuSetting}/>
                    {/if}

                
            </div>
            
            <div>
            {#if tambahAdmin}
                <FDM withRemoveButton = {false} purpose = 'tambah'/>
            {:else if editDetail && detailPane === 'edit'}
            <FDM removeMe={() => edit('tutup')} purpose='edit' formCount=1 formID={id}/>
            {/if}
            </div>
            
        {/if}
    </div>
</div>
{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}

{#if visibilityOverlayKonfirmasi && !konfirmasiPassword}
    <ORP bind:konfirmasi={konfirmasiPassword} bind:visibility={visibilityOverlayKonfirmasi}/>
{/if}
{#if visibilityOverlayKonfirmasiDelete }
    <ORK bind:konfirmasi={konfirmasiDelete} bind:visibility={visibilityOverlayKonfirmasiDelete}> Anda yakin ingin menghapus semua data?</ORK> 
    {/if}
{#if visibilityOverlayKonfirmasiLogout }
    <ORKL bind:konfirmasi={konfirmasiLogout} bind:visibility={visibilityOverlayKonfirmasiLogout} > Anda yakin ingin logout?</ORKL> 
    {/if}
{#if konfirmasiDelete}
    <ORP bind:konfirmasi={konfirmasiWord} bind:visibility={konfirmasiDelete} context='delete'/>
{/if}
{#if konfirmasiWord}
    <ORKW bind:konfirmasi={konfirmasiWord} bind:visibility={konfirmasiWord}/>
{/if}

    

<style>
    .menus{
        color:var(--accent-color)
    }
    @media only screen and (max-width: 900px){
        .boddy{
            flex-direction: column;
        }
    }
</style>
