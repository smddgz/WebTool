package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class VmessTest {
    @Test
    public void getVmess()throws Exception{
        List<Map> maps = new ArrayList<>();
        Base64.Decoder decoder = Base64.getDecoder();
        String s="vmess://eyJ2IjogIjIiLCAicHMiOiAiMzguMTUwLjAuMTcxOjkwMDEiLCAiYWRkIjogIjM4LjE1MC4wLjE3MSIsICJwb3J0IjogOTAwMSwgImFpZCI6IDAsICJ0eXBlIjogIndlY2hhdC12aWRlbyIsICJuZXQiOiAia2NwIiwgInBhdGgiOiAiIiwgImhvc3QiOiAiIiwgImlkIjogIjY4MzhhODU1LTg2ZGQtNDMyMC1iMjgwLTg3N2VhMDJhMDE0YyIsICJ0bHMiOiAibm9uZSJ9";
        s = s.substring(8);
        ObjectMapper om = new ObjectMapper();
        byte[] decode = decoder.decode(s.getBytes(StandardCharsets.UTF_8));
        Map map = om.readValue(decode, Map.class);
        System.out.println(map);
        String ps = (String) map.get("ps");
        String[] split = ps.split(":");
        String host = split[0];
        int port=Integer.parseInt(split[1]);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < 3; i++) {
            map.put("ps",host+":"+(port+i));
            map.put("port",(port+i));
            byte[] bytes = om.writer().writeValueAsBytes(map);
            String s1 = "vmess://"+Base64.getEncoder().encodeToString(bytes)+"\r\n";
            sb.append(s1);
        }

        String s1 = Base64.getEncoder().encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(s1);
    }

    @Test
    public void test1(){
        String s="dm1lc3M6Ly9leUpoWkdRaU9pQWlOVEV1TVRVNUxqRTBNeTR4TkRNaUxDQWlZV2xrSWpvZ01Dd2dJbWh2YzNRaU9pQWlJaXdnSW1sa0lqb2dJamcxTURBeU5qUm1MVGd6TmpJdE5HTXpNQzFoT1RkbUxUWTFPVFJrT0RRME5XVXdZaUlzSUNKdVpYUWlPaUFpZDNNaUxDQWljR0YwYUNJNklDSXZkbTFsYzNNaUxDQWljRzl5ZENJNklEZ3dMQ0FpY0hNaU9pQWlYSFUyWTJRMVhIVTFObVprSUZ4MU5XUm1ORngxT1dWalpVOXViR2x1WlNCVExrRXVVeUlzSUNKMGJITWlPaUFpSWl3Z0luUjVjR1VpT2lBaVlYVjBieUlzSUNKelpXTjFjbWwwZVNJNklDSmhkWFJ2SWl3Z0luTnJhWEF0WTJWeWRDMTJaWEpwWm5raU9pQjBjblZsTENBaWMyNXBJam9nSWlKOQp2bWVzczovL2V5SmhaR1FpT2lBaU5URXVNVFl4TGpNMUxqSXdNQ0lzSUNKaGFXUWlPaUF3TENBaWFHOXpkQ0k2SUNJaUxDQWlhV1FpT2lBaU9XVmpNR1poTUdJdFl6VXdaQzAwWkdSbUxXRXlaREl0WXprNE1qazROalF4WlRrMklpd2dJbTVsZENJNklDSjNjeUlzSUNKd1lYUm9Jam9nSWk5bWNtVmxkbTFsYzNNaUxDQWljRzl5ZENJNklEUTBNeXdnSW5Ceklqb2dJbHgxTlRKaE1GeDFOakptWmx4MU5Ua3lOMXgxT1dJME1WeDFOVE14TjF4MU5URTBZbHgxTnpjd01WeDFOVE0xWVZ4MU9UWXpabHgxTlRKaFlWeDFOelJsTmlCUFZrZ2lMQ0FpZEd4eklqb2dJblJzY3lJc0lDSjBlWEJsSWpvZ0ltRjFkRzhpTENBaWMyVmpkWEpwZEhraU9pQWlZWFYwYnlJc0lDSnphMmx3TFdObGNuUXRkbVZ5YVdaNUlqb2dkSEoxWlN3Z0luTnVhU0k2SUNKemJta3VZMnh2ZFdSbWJHRnlaUzVqYjIwaWZRPT0Kdm1lc3M6Ly9leUoySWpvZ0lqSWlMQ0FpY0hNaU9pQWlYSFUzWmpobFhIVTFObVprSUZKR0lFVnVaMmx1WldWeWFXNW5JaXdnSW1Ga1pDSTZJQ0l4TkRFdU1Ua3pMakl4TXk0eU1TSXNJQ0p3YjNKMElqb2dJalEwTXlJc0lDSnBaQ0k2SUNJeVlqSXhOREV5TWkweE9UQTJMVFF5T0dFdFltSmlOeTFoTURNNVkySmlOMk5rTldNaUxDQWlZV2xrSWpvZ0lqQWlMQ0FpYzJONUlqb2dJbUYxZEc4aUxDQWlibVYwSWpvZ0luZHpJaXdnSW5SNWNHVWlPaUFpYm05dVpTSXNJQ0pvYjNOMElqb2dJbVp5TVM1MGNuVnRjREl3TWpNdWIzSm5JaXdnSW5CaGRHZ2lPaUFpTHpsS1drWkVWRXRGSWl3Z0luUnNjeUk2SUNKMGJITWlMQ0FpYzI1cElqb2dJaUlzSUNKaGJIQnVJam9nSWlKOQp2bWVzczovL2V5SjJJam9nSWpJaUxDQWljSE1pT2lBaVhIVTNaamhsWEhVMU5tWmtJRE5EVDAxY2RUVXhObU5jZFRVelpqaGNkVFJtTURGY2RUUmxNV0ZjZFRkbU5URWlMQ0FpWVdSa0lqb2dJakUxTWk0Mk55NHlNVGd1TWpFMklpd2dJbkJ2Y25RaU9pQWlNVGM1T1RJaUxDQWlhV1FpT2lBaU5USTNOamcxTlRJdFl6SmlNaTAwTkRZd0xUZzVaakV0WlRjME1HTmpNVFExWWprNUlpd2dJbUZwWkNJNklDSXdJaXdnSW5OamVTSTZJQ0poZFhSdklpd2dJbTVsZENJNklDSjNjeUlzSUNKMGVYQmxJam9nSW01dmJtVWlMQ0FpYUc5emRDSTZJQ0paVkVJdFFWZExTaUlzSUNKd1lYUm9Jam9nSWk5M2N5MXdZWFJvSWl3Z0luUnNjeUk2SUNJaUxDQWljMjVwSWpvZ0lpSXNJQ0poYkhCdUlqb2dJaUo5CnRyb2phbjovLzRkMTExYWY4LTUwNzgtMzdlMC1hNTgzLTM0MTU1ZjYwYjMyY0BwaHh2MDAxLmpkMDAwMS50b3A6NDQzMDIjJUU3JUJFJThFJUU1JTlCJUJEK1YyQ1JPU1MuQ09NCnZtZXNzOi8vZXlKMklqb2dJaklpTENBaWNITWlPaUFpWEhVMU16Y3dYSFUxWldFMklGWXlRMUpQVTFNdVEwOU5JaXdnSW1Ga1pDSTZJQ0kyT0M0eE9ETXVPRE11T1RRaUxDQWljRzl5ZENJNklDSTFPVFEzTnlJc0lDSnBaQ0k2SUNKaU9HWTJZVGN4WXkwMk9USTFMVFJsT0RRdFptSTVNeTAyWlRVMFpqaGtPVGhpTWpVaUxDQWlZV2xrSWpvZ0lqQWlMQ0FpYzJONUlqb2dJbUYxZEc4aUxDQWlibVYwSWpvZ0luZHpJaXdnSW5SNWNHVWlPaUFpYm05dVpTSXNJQ0pvYjNOMElqb2dJalk0TGpFNE15NDRNeTQ1TkNJc0lDSndZWFJvSWpvZ0lpOGlMQ0FpZEd4eklqb2dJaUlzSUNKemJta2lPaUFpSWl3Z0ltRnNjRzRpT2lBaUluMD0Kdm1lc3M6Ly9leUpoWkdRaU9pQWlOVEV1TXpndU9Ua3VPVFlpTENBaVlXbGtJam9nTUN3Z0ltaHZjM1FpT2lBaUlpd2dJbWxrSWpvZ0ltSTNOemt6T0RBM0xXSTJZbUl0TkRneU55MWhZV1ZqTFdRME1XRTRPREUzTVRFd1lTSXNJQ0p1WlhRaU9pQWlkM01pTENBaWNHRjBhQ0k2SUNJdlpuSmxaWFp0WlhOeklpd2dJbkJ2Y25RaU9pQTBORE1zSUNKd2N5STZJQ0pjZFRaalpEVmNkVFUyWm1RZ1QxWklJaXdnSW5Sc2N5STZJQ0owYkhNaUxDQWlkSGx3WlNJNklDSmhkWFJ2SWl3Z0luTmxZM1Z5YVhSNUlqb2dJbUYxZEc4aUxDQWljMnRwY0MxalpYSjBMWFpsY21sbWVTSTZJSFJ5ZFdVc0lDSnpibWtpT2lBaUluMD0Kdm1lc3M6Ly9leUpoWkdRaU9pQWlNVFk0TGpFek9DNDJPQzR6TmlJc0lDSmhhV1FpT2lBd0xDQWlhRzl6ZENJNklDSWlMQ0FpYVdRaU9pQWlOVEkzTmpnMU5USXRZekppTWkwME5EWXdMVGc1WmpFdFpUYzBNR05qTVRRMVlqazVJaXdnSW01bGRDSTZJQ0ozY3lJc0lDSndZWFJvSWpvZ0lpOTNjeTF3WVhSb0lpd2dJbkJ2Y25RaU9pQXhOREkxTWl3Z0luQnpJam9nSWx4MU4yWTRaVngxTlRabVpDQldNa05TVDFOVExrTlBUU0lzSUNKMGJITWlPaUFpSWl3Z0luUjVjR1VpT2lBaVlYVjBieUlzSUNKelpXTjFjbWwwZVNJNklDSmhkWFJ2SWl3Z0luTnJhWEF0WTJWeWRDMTJaWEpwWm5raU9pQjBjblZsTENBaWMyNXBJam9nSWlKOQp2bWVzczovL2V5SjJJam9nSWpJaUxDQWljSE1pT2lBaVhIVTBaVEJoWEhVMlpEYzNYSFUxWlRBeUlGeDFPREExTkZ4MU9UQXhZU0lzSUNKaFpHUWlPaUFpYzJoamRUQXhMbmhqTVRnNExtNWxkQ0lzSUNKd2IzSjBJam9nSWpFd01EQTFJaXdnSW1sa0lqb2dJbU5pTnpBd01XTTNMV1UwT1RVdE5ERmhZeTFpT1RReUxXWXlOV1kyTURVeU16UXhOQ0lzSUNKaGFXUWlPaUFpTUNJc0lDSnpZM2tpT2lBaVlYVjBieUlzSUNKdVpYUWlPaUFpZEdOd0lpd2dJblI1Y0dVaU9pQWlibTl1WlNJc0lDSm9iM04wSWpvZ0lpSXNJQ0p3WVhSb0lqb2dJaUlzSUNKMGJITWlPaUFpSWl3Z0luTnVhU0k2SUNJaUxDQWlZV3h3YmlJNklDSWlmUT09CnZtZXNzOi8vZXlKaFpHUWlPaUFpTWpFeUxqRXlPUzR5TUM0M0lpd2dJbUZwWkNJNklEQXNJQ0pvYjNOMElqb2dJaUlzSUNKcFpDSTZJQ0psTTJaaU9UbGxZeTFpTWpjMUxURXhaV1F0T0RoaU5DMDFNalUwTURBd01HWmhabUVpTENBaWJtVjBJam9nSW5keklpd2dJbkJoZEdnaU9pQWlMM1p3Ym1waGJuUnBkQ0lzSUNKd2IzSjBJam9nTVRBd01EQXNJQ0p3Y3lJNklDSmNkVFpqWkRWY2RUVTJabVFnVDA1TVNVNUZJRk11UVM1VExseDFOalUzTUZ4MU5qTTJaVngxTkdVeVpGeDFOV1pqTXlJc0lDSjBiSE1pT2lBaUlpd2dJblI1Y0dVaU9pQWlZWFYwYnlJc0lDSnpaV04xY21sMGVTSTZJQ0poZFhSdklpd2dJbk5yYVhBdFkyVnlkQzEyWlhKcFpua2lPaUIwY25WbExDQWljMjVwSWpvZ0lpSjkKdm1lc3M6Ly9leUpoWkdRaU9pQWlNVFV5TGpZNUxqRTVOeTQzTkNJc0lDSmhhV1FpT2lBd0xDQWlhRzl6ZENJNklDSWlMQ0FpYVdRaU9pQWlZamhoTm1KbU5UZ3RORGcxWVMwME1EUTJMV0l6T0RZdFlqTTJOakZpWmpZMVpXWm1JaXdnSW01bGRDSTZJQ0ozY3lJc0lDSndZWFJvSWpvZ0lpOWlZaUlzSUNKd2IzSjBJam9nTVRJek5EVXNJQ0p3Y3lJNklDSmNkVGRtT0dWY2RUVTJabVFnVmpKRFVrOVRVeTVEVDAwaUxDQWlkR3h6SWpvZ0lpSXNJQ0owZVhCbElqb2dJbUYxZEc4aUxDQWljMlZqZFhKcGRIa2lPaUFpWVhWMGJ5SXNJQ0p6YTJsd0xXTmxjblF0ZG1WeWFXWjVJam9nZEhKMVpTd2dJbk51YVNJNklDSWlmUT09CnZtZXNzOi8vZXlKaFpHUWlPaUFpZGpFeU5TNTBiMlJrYm5NdWRHc2lMQ0FpWVdsa0lqb2dJakFpTENBaVlXeHdiaUk2SUNJaUxDQWlhRzl6ZENJNklDSjJNVEkxTG5SdlpHUnVjeTUwYXlJc0lDSnBaQ0k2SUNKaE1qVTRPREZtTXkwNU5qZG1MVE15TmpVdFltTTNaaTA1WlRZMk9EVTNZakF4Tm1JaUxDQWlibVYwSWpvZ0luZHpJaXdnSW5CaGRHZ2lPaUFpTDNWemRqRXlOVTQ1TTI1Nklpd2dJbkJ2Y25RaU9pQWlPREFpTENBaWNITWlPaUFpWEhVM1pqaGxYSFUxTm1aa0lFTnNiM1ZrUm14aGNtVmNkVGd5T0RKY2RUY3dZamtpTENBaWMyTjVJam9nSW1GMWRHOGlMQ0FpYzI1cElqb2dJaUlzSUNKMGJITWlPaUFpSWl3Z0luUjVjR1VpT2lBaUlpd2dJbllpT2lBaU1pSjkKdm1lc3M6Ly9leUoySWpvZ0lqSWlMQ0FpY0hNaU9pQWlYSFUzWmpobFhIVTFObVprSUZZeVExSlBVMU11UTA5Tklpd2dJbUZrWkNJNklDSjJaR1V4TGpCaVlXUXVZMjl0SWl3Z0luQnZjblFpT2lBaU5EUXpJaXdnSW1sa0lqb2dJamt5TnpBNU5HUXpMV1EyTnpndE5EYzJNeTA0TlRreExXVXlOREJrTUdKallXVTROeUlzSUNKaGFXUWlPaUFpTUNJc0lDSnpZM2tpT2lBaVlYVjBieUlzSUNKdVpYUWlPaUFpZDNNaUxDQWlkSGx3WlNJNklDSnViMjVsSWl3Z0ltaHZjM1FpT2lBaWRtUmxNUzR3WW1Ga0xtTnZiU0lzSUNKd1lYUm9Jam9nSWk5amFHRjBJaXdnSW5Sc2N5STZJQ0owYkhNaUxDQWljMjVwSWpvZ0lpSXNJQ0poYkhCdUlqb2dJaUo5CnZtZXNzOi8vZXlKMklqb2dJaklpTENBaWNITWlPaUFpWEhVM1pqaGxYSFUxTm1aa0lGWXlRMUpQVTFNdVEwOU5JaXdnSW1Ga1pDSTZJQ0l4TmpndU1UTTRMalk0TGpNMklpd2dJbkJ2Y25RaU9pQWlNVFF5TlRJaUxDQWlhV1FpT2lBaU5USTNOamcxTlRJdFl6SmlNaTAwTkRZd0xUZzVaakV0WlRjME1HTmpNVFExWWprNUlpd2dJbUZwWkNJNklDSXdJaXdnSW5OamVTSTZJQ0poZFhSdklpd2dJbTVsZENJNklDSjNjeUlzSUNKMGVYQmxJam9nSW01dmJtVWlMQ0FpYUc5emRDSTZJQ0l4TmpndU1UTTRMalk0TGpNMklpd2dJbkJoZEdnaU9pQWlMM2R6TFhCaGRHZ2lMQ0FpZEd4eklqb2dJaUlzSUNKemJta2lPaUFpSWl3Z0ltRnNjRzRpT2lBaUluMD0Kdm1lc3M6Ly9leUpoWkdRaU9pQWlNak11TWpJM0xqTTRMak01SWl3Z0luWWlPaUFpTWlJc0lDSndjeUk2SUNKY2RUZG1PR1ZjZFRVMlptUWdRMnh2ZFdSR2JHRnlaVngxTlRFMlkxeDFOVE5tT0VORVRseDFPREk0TWx4MU56QmlPU2h6YUc5d2FXWjVLU0lzSUNKd2IzSjBJam9nTkRRekxDQWlhV1FpT2lBaU5UWmhNakU0T0dJdE1tRmlOeTAwTURKakxXSTVZamd0TXpRNE5EZG1aR1l3T1RVNElpd2dJbUZwWkNJNklDSXdJaXdnSW01bGRDSTZJQ0ozY3lJc0lDSjBlWEJsSWpvZ0lpSXNJQ0pvYjNOMElqb2dJbTl3YkdjeExucG9kV3BwWTI0eUxtTnZiU0lzSUNKd1lYUm9Jam9nSWk4MVVVNVNUMU5TVmlJc0lDSjBiSE1pT2lBaWRHeHpJbjA9";
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(s.getBytes());
        String s1 = new String(decode);
        System.out.println(s1);

    }
}
