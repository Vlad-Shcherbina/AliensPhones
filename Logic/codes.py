reusable = [
    "Reset",
    "DebugProcess",

    "Cross",
    "Safe",
    "Gasp",
    "Resp",
    "Mask",

    "Yellow",
    "Blue",

    "LeftArmDemo",
    "RightArmDemo",
    "LeftLegDemo",
    "RightLegDemo",
    "TorsoDemo",
]

unique = [
    #"Surfactant",
    #"Antialvin",
    #"Travian",
    "Sambuca",

    "Surfaktant",
    "AntiAlvine",
    "PlasmaToxone",
]

"""
    "AbdominalSurgery",
    "BenzylAlienat",
    "CardCodes.!py",
    "Citosolute",
    "Cyprostane",
    "DichloFlu",
    "Ehinospore",
    "Erpoiteen",
    "EssenceCita",
    "Facehugger",
    "FirstAidKit",
    "FirstAidKitLeftArm",
    "FirstAidKitLeftLeg",
    "FirstAidKitRightArm",
    "FirstAidKitRightLeg",
    "FirstAidKitTorso",
    "Friz",
    "FybrinogenLocal",
    "FybrinogenLocalLeftArm",
    "FybrinogenLocalLeftLeg",
    "FybrinogenLocalRightArm",
    "FybrinogenLocalRightLeg",
    "FybrinogenLocalTorso",
    "GetAnalysis",
    "Healer",
    "Klodil",
    "MetanolCyanide",
    "MethMorthine",
    "MonoFloxacyne",
    "Perftoran",
    "PlasmoDialite",
    "PolyPiroCetam",
    "RemovalSurgery",
    "Requre",
    "Sambuca",
    "StreptonideTonine",
    "Urcaine",
    "UrcaineLeftArm",
    "UrcaineLeftLeg",
    "UrcaineRightArm",
    "UrcaineRightLeg",
    "UrcaineTorso",
    "VascularSurgery",
    "VascularSurgeryLeftArm",
    "VascularSurgeryLeftLeg",
    "VascularSurgeryRightArm",
    "VascularSurgeryRightLeg",
    "VascularSurgeryTorso",
    "Warfareen",
    "WarfareenSalicylat",
]
"""


def get_code_path(name):
    file_name = name
    if name in reusable:
        file_name = 'reusable/'+file_name
    file_name = '../../Cards/codes/'+file_name
    if file_name.endswith('Demo'):
        file_name = file_name[:-4]
    return file_name


def get_codes(name):
    return [c.strip() for c in open(get_code_path(name)).readlines()]