# 빌드 폴더 정리 스크립트
$outPath = "out"
$buildPath = "build"

Write-Host "빌드 폴더 정리 중..."

if (Test-Path $outPath) {
    Remove-Item -Recurse -Force $outPath
    Write-Host "✓ out 폴더가 삭제되었습니다."
} else {
    Write-Host "- out 폴더가 없습니다."
}

if (Test-Path $buildPath) {
    Remove-Item -Recurse -Force $buildPath
    Write-Host "✓ build 폴더가 삭제되었습니다."
} else {
    Write-Host "- build 폴더가 없습니다."
}

Write-Host "정리 완료! 프로젝트를 다시 빌드하세요."


