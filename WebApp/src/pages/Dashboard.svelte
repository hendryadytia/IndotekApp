<script>
     import {apiurl} from '../helper/apiurl.js'
    import {isLogin} from '../stores/LoginCondition'
    import { useNavigate, useLocation } from "svelte-navigator";
    import {clearIntv} from "../helper/clearintervals.js"
    import NotificationChanges from "../components/NotificationChanges.svelte"

    import Topbar from '../components/Topbar.svelte'
    import X from 'jquery'
    import DashboardCards from '../components/DashboardCards.svelte'
    import Notifications from '../components/Notifications.svelte'
    import UserListSmall from '../components/UserListSmall.svelte'
    import DLFC from '../components/DataListForCard.svelte'
    import ORK from '../components/OvrReqKonfirm.svelte'

    let number = 3
    let splashscreen = false
    let triggernotif =false
    let headnotif,bodynotif,responsestatus
    const navigate = useNavigate();
    const location = useLocation();
    let login
    let notifdata = []
    let orkvisibility,jenism,namap,idmesin,diagnosa,biaya,estselesai,konfirmasi,idteknisi,namateknisi,iddiagnosa
    let jmlBulanIni,sekarang,kemarin,performabulanini,selesaibulankemarin,selesaibulanini,performabulankemarin,pelangganterkini=[],teknisiterkini=[]
    let refreshIntervalId
    var currformatter = new Intl.NumberFormat('id-ID', {
                                            style: 'currency',
                                            currency: 'IDR',})

     isLogin.subscribe(value => {
		login = value;
	});

    jmlBulanIni=sekarang=kemarin=selesaibulankemarin=selesaibulanini=0;
    performabulanini=performabulankemarin= "-"

    function ellipsissubstr(text,maxlength){
        if(text.length > maxlength){
            return text.substring(0,maxlength).concat('..')
        }else{
            return text
        }
    }

    const bukaDialogAcc = (id) => {
        orkvisibility = true
        iddiagnosa = id
        X.ajax({
            url:apiurl+"getdashboardnotif",
            type:"GET",
            data:{
                id_mesin : id
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                jenism =result.data.jenis_mesin
                namap =result.data.nama
                idmesin =result.data.id_mesin
                diagnosa   =result.data.diagnosa
                biaya       =result.data.biaya
                estselesai  =result.data.est_selesai
                idteknisi = result.data.id_teknisi
                namateknisi = result.data.nama_teknisi

            }        
            },
        )
    };

    const accMesin = (id,idm) => {
        orkvisibility = false
        cekNotif()
        X.ajax({
            url:apiurl+"adminaccpengajuan",
            type:"POST",
            data:{
                id_diagnosa:id,
                id_mesin : idm,
                id_teknisi : idteknisi
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                    headnotif= "Berhasil ACC Diagnosa"
                    triggernotif = "show"
                    responsestatus = "sukses"
                
            }        
            },
        )
    }

    function getDataDashboard(){
        X.ajax({
            url:apiurl+"dashboardstatistik",
            type:"get",
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                if(result.code == "200"){
                    jmlBulanIni = result.blok_mesin.bulan_ini||"0"
                    sekarang = result.blok_mesin.sekarang||"0"
                    kemarin = result.blok_mesin.kemarin||"0"
                    performabulanini = result.blok_performa.performa_bulan_ini.est_selesai||"-"
                    selesaibulanini = result.blok_performa.bulan_ini||"0"
                    selesaibulankemarin = result.blok_performa.bulan_kemarin||"0"
                    performabulankemarin = result.blok_performa.performa_bulan_kemarin.est_selesai||"-"
                    pelangganterkini = result.blok_pelanggan
                    teknisiterkini = result.blok_teknisi
                }
            },
           
                        
            },
        )
    }

    getDataDashboard()

    if (login != "terlogin"){
        splashscreen = true
        refreshIntervalId = setInterval(
            ceklogin()     
       
        , 3000);


     
      
    }
    function ceklogin(){
        X.ajax({
            url:apiurl+"logincheck",
            type:"POST",
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                if(result.code == "401"){
                    navigate("/login", {
                    state: { from: $location.pathname },
                    replace: true});
                    splashscreen = true
                }else{
                    splashscreen = false
                    isLogin.set("terlogin")
                    clearInterval(refreshIntervalId)
                }
            },
           
                        
            },
        )
    }
    const cekNotif = () => {
        X.ajax({
            url:apiurl+"getdashboardnotif",
            type:"GET",
           
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                
           
                result.data.forEach(element => {
                    let index = notifdata.findIndex(obj => obj.id_diagnosa == element.id_diagnosa)
                    console.log(index);
                    if(index === -1){
                        notifdata.push(element)
                        headnotif= "ACC Diagnosa"
                        triggernotif = "show"
                        responsestatus = "sukses"
                    }
                    // else{
                    //     notifdata = []
                    //     notifdata.push(element)
                    // }

                });
                notifdata = result.data 
               
                setTimeout(() => {
                    cekNotif()
                }, 5000);
            }
                        
            },
        )
           
       
    }
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

    cekNotif()


    clearIntv()
    // let loopceknotif = setInterval(function() { 
    //     cekNotif()
    // }, 5000);
</script> 

<div style="display: block; width: 100%; padding-left: 35px">
    <Topbar icon="home" namaHalaman="Dashboard"/>
    <div style="display: flex;">
        <div style="width: 350px;">
            <p style="font-size: 20px; font-family: Helvetica; font-weight:bold; margin-bottom: 21px">Notifikasi</p>
            {#if (notifdata.length > 0)}
                
            {#each notifdata as nd}
                <Notifications isinotif={nd.jenis_mesin} jamMasuk={nd.tgl_pengajuan} onclick={() => bukaDialogAcc(nd.id_diagnosa)}/>
            {/each}
            {:else}
            Belum ada notifikasi
            {/if}
        </div>
        <div style="display: block;width: 100%;">
            <div style="display:flex; gap:60px; margin-bottom: 60px">
                <DashboardCards withPlus judul="Mesin" linkto="tambahmesin">
                    <div style="display:flex;width: 100%; height:100%; gap: 10px; margin-top: -10px">
                        <div style="position: relative; display: flex; height: 192px; width: 100%; background-color: #EFEAEA; color: #707070; justify-content: center;" >
                            <p style="margin-top: 15px;font-size: 130px; font-family: Helvetica; ">{jmlBulanIni}</p>
                            <p style="position: absolute; font-size: 14px; font-family: Helvetica; margin: 0; color:#A71818; bottom: 8px;">Masuk Bulan Ini</p>
                        </div>
                        <div style="display: flex; flex-direction: column; gap: 10px;">
                            <div style="position: relative; display: flex; height: calc(50% - 5px); width: 136px; background-color: #EFEAEA; color: #707070; justify-content: center;" >
                                <p style="margin-top: 5px;font-size: 56px; font-family: Helvetica; ">{sekarang}</p>
                                <p style="position: absolute; font-size: 14px; font-family: Helvetica; margin: 0; color:#A71818; bottom: 8px;">Sekarang</p>
                            </div>
                            <div style="position: relative; display: flex; height: calc(50% - 5px); width: 136px; background-color: #EFEAEA; color: #707070; justify-content: center;" >
                                <p style="margin-top: 5px;font-size: 56px; font-family: Helvetica; ">{kemarin}</p>
                                <p style="position: absolute; font-size: 14px; font-family: Helvetica; margin: 0; color:#A71818; bottom: 8px;">Kemarin</p>
                            </div>
                        </div>
                    </div>
                </DashboardCards>
                <DashboardCards judul="Performa" icon="performa_dashboard">
                    <div style="display: flex; width: 100%; height:100%; margin-top: -10px; gap:10px">
                        <div style="position: relative; display: flex; flex-direction: column; height: 192px; width: 50%; background-color: #EFEAEA; color: #707070; align-items: center;" >
                            <p style="position: absolute; font-size: 14px; font-family: Helvetica; margin: 0; color:#A71818; top:2px; left:6px">Selesai Bulan Ini</p>
                            <p style="margin-top: 20px;font-size: 90px; font-family: Helvetica;margin-bottom: 0px; ">{selesaibulanini}</p>
                            <p style="font-size: 12px; font-family: Helvetica; margin:-5px 0 0 0">Mesin</p>
                            <hr style=" width: 60%;">
                            <p style="font-size: 12px; font-family: Helvetica; margin:0">Rerata Selesai</p>
                            <p style="font-size: 25px; font-family: Helvetica; margin:10px 0 0 0;">{performabulanini.substring(0,3)} hari</p>
                
                        </div>
                        <div style="position: relative; display: flex; flex-direction: column; height: 192px; width: 50%; background-color: #EFEAEA; color: #707070; align-items: center;" >
                            <p style="position: absolute; font-size: 14px; font-family: Helvetica; margin: 0; color:#A71818; top:2px; left:6px">Bulan Kemarin</p>
                            <p style="margin-top: 20px;font-size: 90px; font-family: Helvetica;margin-bottom: 0px; ">{selesaibulankemarin}</p>
                            <p style="font-size: 12px; font-family: Helvetica; margin:-5px 0 0 0">Mesin</p>
                            <hr style=" width: 60%;">
                            <p style="font-size: 12px; font-family: Helvetica; margin:0">Rerata Selesai</p>
                            <p style="font-size: 25px; font-family: Helvetica; margin:10px 0 0 0;">{performabulankemarin.substring(0,3)} hari</p>
                        </div>
                    </div>
                </DashboardCards>
            </div>
            <div style="display:flex; gap:60px; margin-bottom: 60px">
                <DashboardCards judul="Pelanggan" icon="customer_dashboard" linkto="daftarpelanggan">
                    {#each pelangganterkini as p, i}
                        <UserListSmall urutan={i} nama={ellipsissubstr(p.nama,12)} alamat={p.no_telp} tanggal={ellipsissubstr(p.alamat,25)} />
                    {/each}
                </DashboardCards>
                <DashboardCards withPlus judul="Teknisi" icon="teknisi_dashboard" linkto="daftarteknisi">
                    {#each teknisiterkini as t,i}
                        <UserListSmall urutan={i} nama={ellipsissubstr(t.nama,12)} alamat={t.no_telp} tanggal={ellipsissubstr(t.alamat,25)}/>
                    {/each}    
                </DashboardCards>
            </div>
        </div>
    </div>
</div>

{#if orkvisibility}
    <ORK bind:visibility={orkvisibility} width=600 bind:konfirmasi={konfirmasi} judul={"ACC Biaya"} jawab={()=> accMesin(iddiagnosa,idmesin)} titleLanjutbtn="ACC">
        <DLFC title="Teknisi" style="margin-bottom:10px" isi = {namateknisi}/>
        <DLFC title="Jenis Mesin" style="margin-bottom:10px" isi = {jenism}/>
        <DLFC title="Nama Pemilik" style="margin-bottom:10px" isi = {namap}/>
        <DLFC title="Diagnosa" style="margin-bottom:10px" isi = {diagnosa}/>
        <DLFC title="Biaya" style="margin-bottom:10px" isi = {currformatter.format(biaya)}/>
        <DLFC title="Est. Waktu" style="margin-bottom:10px" isi = {estselesai + " hari"}/>
    </ORK>
{/if}

{#if splashscreen}
<div style="flex-direction:column;position: absolute;width:100vw;height:100vh; background-color:white; display:flex;align-items:center;justify-content:center">
    <svg class="logo" xmlns="http://www.w3.org/2000/svg" width="90" height="90" viewBox="0 0 45.603 45.603">
        <g id="logo_indotek" data-name="logo indotek" transform="translate(0)">
          <path id="Path_9" data-name="Path 9" d="M21.158,43.617H18.421v.758H17.89v-.758H15.237L9.416,38.28v.466H8.885v-.953L7.344,36.381l.179-.195.179-.194.726.665.457.419V14.82l-.748.013c-.221,0-.429,0-.614,0v-.53c.491,0,.917-.008,1.357-.016,4.731-.087,10.734-.2,12.535,7.8l-.258.058h.264V38.405h-.53V22.175c-.016-.067-.031-.135-.048-.2a9.67,9.67,0,0,0-2.424-4.708V43.086h2.737v.531ZM9.416,37.562l2.471,2.266V14.837c-.836-.044-1.669-.039-2.471-.026V37.562Zm3,2.752,3.024,2.773H17.89v-26.3a7.742,7.742,0,0,0-2.471-1.343V42.456h-.531V15.281a13.409,13.409,0,0,0-2.472-.409Z" transform="translate(-1.302 -2.53)" fill="#094" fill-rule="evenodd"/>
          <path id="Path_10" data-name="Path 10" d="M31.357,43.617h2.736v.758h.531v-.758h2.653L43.1,38.28v.466h.531v-.953l1.541-1.413-.179-.195-.179-.194-.725.665-.457.419V14.82l.749.013c.22,0,.429,0,.613,0v-.53c-.491,0-.917-.008-1.356-.016-4.732-.087-10.735-.2-12.536,7.8l.258.058h-.266V38.405h.531V22.175c.016-.067.031-.135.048-.2a9.677,9.677,0,0,1,2.424-4.708V43.086H31.357ZM43.1,37.562l-2.471,2.266V14.837c.836-.044,1.669-.039,2.471-.026V37.562Zm-3,2.752-3.024,2.773H34.624v-26.3a7.751,7.751,0,0,1,2.471-1.343V42.456h.531V15.281a13.408,13.408,0,0,1,2.472-.409Z" transform="translate(-5.511 -2.53)" fill="#094" fill-rule="evenodd"/>
          <path id="Path_11" data-name="Path 11" d="M22.8,0a22.812,22.812,0,0,1,22.25,17.8H38.463A16.441,16.441,0,0,0,6.673,19.619H45.383A22.805,22.805,0,0,1,.551,27.8H7.141a16.442,16.442,0,0,0,31.79-1.821H.22A22.8,22.8,0,0,1,22.8,0" transform="translate(0)" fill="#e60013"/>
          <path id="Path_12" data-name="Path 12" d="M23.046,10.49V9.517a1.046,1.046,0,0,1,.328-.815,1.178,1.178,0,0,1,.824-.281h5.239a2.753,2.753,0,0,1,.52.044v0a1.053,1.053,0,0,1,.482.212.948.948,0,0,1,.288.564,3.331,3.331,0,0,1,.049.612c0,.335-.009.613-.027.833a1.87,1.87,0,0,1-.123.593.956.956,0,0,1-.368.422,1.294,1.294,0,0,1-.492.179.849.849,0,0,0-.545.272,1.047,1.047,0,0,0-.177.662V35.973a.493.493,0,0,0,.105.308,1.148,1.148,0,0,0,.434.318,3.672,3.672,0,0,1,.513.285,1.215,1.215,0,0,1,.38.388l0,0a1.389,1.389,0,0,1,.144.444,2.978,2.978,0,0,1,.038.494v.7a3.152,3.152,0,0,1-.038.542.782.782,0,0,1-.327.558,1.035,1.035,0,0,1-.421.13,3.864,3.864,0,0,1-.438.022H24.025a1.14,1.14,0,0,1-.717-.215.867.867,0,0,1-.339-.723q0-.655.026-1.094a3.142,3.142,0,0,1,.094-.674,1.016,1.016,0,0,1,.415-.548,1.966,1.966,0,0,1,.606-.271,1.177,1.177,0,0,0,.527-.262.377.377,0,0,0,.1-.276V13.256a3.871,3.871,0,0,0-.038-.595,1.3,1.3,0,0,0-.1-.346.5.5,0,0,0-.148-.17,1.665,1.665,0,0,0-.4-.217,2.975,2.975,0,0,1-.45-.224,1.186,1.186,0,0,1-.365-.327l0,0a1.1,1.1,0,0,1-.159-.429,2.644,2.644,0,0,1-.035-.454Zm1.061-.972v.972a1.709,1.709,0,0,0,.017.276c0,.022-.008.012-.008.016s.012,0,.046.024a1.982,1.982,0,0,0,.293.141,2.683,2.683,0,0,1,.656.371,1.535,1.535,0,0,1,.452.547,2.364,2.364,0,0,1,.183.633,4.893,4.893,0,0,1,.055.76V36.1a1.4,1.4,0,0,1-.41,1.018,2.188,2.188,0,0,1-1,.54.96.96,0,0,0-.282.117s0,0,0-.007l0-.006,0,0a2.535,2.535,0,0,0-.047.427c-.013.223-.02.526-.021.909h5.408l.159,0c0-.055,0-.118,0-.188v-.7a2.061,2.061,0,0,0-.021-.321.4.4,0,0,0-.028-.108.473.473,0,0,1-.055-.03,2.523,2.523,0,0,0-.36-.2,2.2,2.2,0,0,1-.829-.633,1.532,1.532,0,0,1-.324-.95V12.817a2.056,2.056,0,0,1,.413-1.325,1.859,1.859,0,0,1,1.2-.652h0a.393.393,0,0,0,.07-.016c.007-.039.016-.114.025-.222.015-.185.023-.434.023-.745A2.728,2.728,0,0,0,29.7,9.5a2,2,0,0,0-.259-.015H24.2c-.072,0-.109,0-.111,0s.019,0,.019.032Zm5.647-.03v0c.015.011.021.016,0,0Zm0,0s0,0,0,0l0,0ZM24.1,37.76l0,.007,0,.006c0-.01,0-.026,0-.013" transform="translate(-4.071 -1.493)" fill="#e60013"/>
          <path id="Path_13" data-name="Path 13" d="M26.029,13.485V36.334c0,1.352-1.457,1.509-1.691,1.674l0-.007,0-.006-.07,1.339,5.566,0-.1-1.35c-.431-.3-1.512-.589-1.512-1.78V13.046a1.839,1.839,0,0,1,1.614-1.978.393.393,0,0,0,.07-.016l.029-1.326-5.5-.014-.092.034c0,2.02-.155.884,1,1.8a2.268,2.268,0,0,1,.689,1.94" transform="translate(-4.3 -1.721)" fill="#fff" fill-rule="evenodd"/>
        </g>
    </svg>
    <p style="margin-top: 30px;">LOADING...</p>

</div>
{/if}

{#if triggernotif == "show"}
        <NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
    {/if}



